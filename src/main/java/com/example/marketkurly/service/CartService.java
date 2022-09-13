package com.example.marketkurly.service;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Cart;
import com.example.marketkurly.model.User;
import com.example.marketkurly.repository.CartRepository;
import com.example.marketkurly.repository.UserRepository;
import com.example.marketkurly.service.impl.CartServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class CartService implements CartServiceImpl {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public ResponseDto<?> getAllCartList(HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;

        var checked= checkResponse.getData();
        ArrayList<Product> products;
        try {
            products= cartRepository.findAllByProductId();
        } catch (Exception e){
            System.err.println(e + "productId parsing err");
        }
        return ResponseDto.success(products);
    }

    @Override
    @Transactional
    public ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;

        User user= (User) checkResponse.getData();

        requestCartDto.builder()
                .user(user);

        Cart cart;
        try {
            cart= new Cart(requestCartDto);
        } catch (Exception e){
            System.err.println(e + "cart request 정보 부족");
        }

        return ResponseDto.success(
                ResponseCartDto.builder()
                        .id(cart.getId())
                        .productIds(cart.getProductId())
                        .user(cart.getUser())
                        .price(cart.getPrice())
                        .sum(cart.getSum())
                        .address(cart.getAddress())
        );
    }

    @Override
    @Transactional
    public ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<?> deleteCartList(HttpServletRequest request) {
        return null;
    }


    @Transactional
    private ResponseDto<?> validateCheck(HttpServletRequest request) {
        if(null == request.getHeader("RefreshToken") || null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }
        User user = validateUser(request);
        if(null == user) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        return ResponseDto.success(user);
    }

    @Transactional
    public User validateUser(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("RefreshToken"))) {
            return null;
        }
//        @todo Member를 User로 고쳐야 할 수도. 인증 부분 어떻게 되는지 몰라서 그대로 씀.
        return tokenProvider.getMemberFromAuthentication();
    }
}
