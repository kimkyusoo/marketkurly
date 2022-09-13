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
    private final ProductRepository productRepository;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public ResponseDto<?> getAllCartList(HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;
        
//        @todo 로그인 인증 과정만 제대로 처러하면 뒤에 처리된 객체는 필요 없을 듯
//        var checked= checkResponse.getData();
        
        ArrayList<Product> products;
        try {
            products= cartRepository.findAllByProductId();
        } catch (Exception e){
            return ResponseDto.fail("RDS err", e + "productId parsing err");
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


        Cart cart= new Cart(requestCartDto);

        ArrayList<Product> productArrayList;
        try {
            productArrayList= cartRepository.findAllByProductId();
        } catch (Exception e){
            return ResponseDto.fail("RDS err", e + "productId parsing err");
        }

        String sum= "";
        for (var productId : productArrayList){
//            @todo product 객체 가져와야 가격 정보를 가져올 수 있는데 없어서 임의로 넣음.
            try {
                String price= productRepository.findByProduct(productId);
            }catch (Exception e){
                return ResponseDto.fail("RDS err", e + "productId parsing err");
            }
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
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;
        User user= (User) checkResponse.getData();

        return null;
    }

    //        reset all
    @Override
    @Transactional
    public ResponseDto<?> deleteCartList(HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;
        
        try {
            cartRepository.deleteAll();
        }catch (Exception e){
            return ResponseDto.fail("delete err", "장바구니 삭제 에러");
        }

        return ResponseDto.success("정상적으로 장바구니가 비워졌습니다");
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
