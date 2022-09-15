package com.example.marketkurly.service;


import com.example.marketkurly.dto.request.LoginRequestDto;
import com.example.marketkurly.dto.request.TokenDto;
import com.example.marketkurly.dto.request.SignupRequestDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.dto.response.UserResponseDto;
import com.example.marketkurly.jwt.TokenProvider;
import com.example.marketkurly.model.User;
import com.example.marketkurly.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
//  회원가입. 유저가 존재하는지, 비밀번호와 비밀번호확인이 일치하는지의 여부를 if문을 통해 확인하고 이를 통과하면 user에 대한 정보를 생성.
    public ResponseDto<?> createUser(SignupRequestDto requestDto) {

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .build();

        userRepository.save(user);
        return ResponseDto.success("회원가입 완료");
    }

//    public ResponseDto<?> checkUser(SignupRequestDto requestDto) {
//        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
//        if (null != isPresentUser(requestDto.getUsername()))
//            return ResponseDto.fail("DUPLICATED_USERNAME", "중복된 ID 입니다.");
//        return ResponseDto.success("사용 가능한 ID입니다.");
//    }
//
//    public ResponseDto<?> checkNickname(SignupRequestDto requestDto) {
//        Optional<User> nickname = userRepository.findByNickname(requestDto.getNickname());
//        if (null != isPresentNickname(requestDto.getNickname()))
//            return ResponseDto.fail("DUPLICATED_NICKANAME", "중복된 닉네임 입니다.");
//        return ResponseDto.success("사용 가능한 닉네임 입니다.");
//    }
    @Transactional(readOnly = true)
    public User isPresentUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }
//
//    @Transactional(readOnly = true)
//    public User isPresentNickname(String nickname) {
//        Optional<User> optionalNickname = userRepository.findByNickname(nickname);
//        return optionalNickname.orElse(null);
//    }

    @Transactional
//  로그인. 가입할때 사용된 정보를 SignupRequestDto에 보내고 HttpServletResponse에 속한 권한이 확인.
//  사용자의 아이디가 존재하지 않거나 비밀번호확인이 일치하지 않았을 때 오류 메시지를 출력.
//  정상일 경우 tokenProvider를 통하여 유저에게 토큰을 생성하고 이를 헤더에 보낸다.
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        User user = isPresentUser(requestDto.getUsername());
        if (null == user) {
            return ResponseDto.fail("USER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        if (!user.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_USER", "비밀번호가 틀렸습니다..");
        }


        TokenDto tokenDto = tokenProvider.generateTokenDto(user);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                UserResponseDto.builder()
                        .id(user.getId())
                        .nickname(user.getNickname())
                        .build()
        );
    }
    //  로그아웃. HttpServletRequest에 있는 권한을 보내 토큰을 확인하여 일치하지 않거나 유저 정보가 없을 경우 오류 메시지를 출력
    //  정상일 경우 tokenProvider에 유저에게 있는 리프레시토큰 삭제를 진행

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("refreshtoken"))) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        User user = tokenProvider.getUserFromAuthentication();
        if (null == user) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(user);
    }

    //  TokenDto와 HttpServletResponse 응답을 헤더에 보낼 경우
    //  권한과 tokenDto에 있는 AccessToken을 추가
    //  Refresh-token을 추가
    //  AccessToken의 유효기간을 추가한다.
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("refreshtoken", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
}
