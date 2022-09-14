package com.example.marketkurly.service;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.jwt.TokenProvider;
import com.example.marketkurly.model.Cart;
import com.example.marketkurly.model.Product;
import com.example.marketkurly.model.User;
import com.example.marketkurly.repository.CartRepository;
import com.example.marketkurly.repository.ProductReposioty;
import com.example.marketkurly.repository.UserRepository;
import com.example.marketkurly.service.impl.CartServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartService implements CartServiceImpl {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductReposioty productRepository;
    private TokenProvider tokenProvider;

    @Override
    @Transactional
    public ResponseDto<?> getAllCartList(HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;

        ArrayList<Product> products;
        try {
            products= cartRepository.findAllByProductIds();
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

        List<Product> productArrayList;
        try {
            productArrayList= cartRepository.findAllByProductIds();
        } catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "productId parsing err");
        }

        Long sum= calculateAllPrice(productArrayList);
        cart.updateSumPrice(sum);

        cartRepository.save(cart);

        return ResponseDto.success(
                ResponseCartDto.builder()
                        .id(cart.getId())
                        .productIds(cart.getProductIds())
                        .user(cart.getUser())
                        .sum(cart.getSum())
                        .address(cart.getAddress())
        );
    }
    private static Long calculateAllPrice(List<Product> productArrayList) {
        Long sum= 0L;
        for (var product : productArrayList){
            int price = product.getPrice();
            sum += price;
        }
        return sum;
    }

    @Override
    @Transactional
    public ResponseDto<?> updateCartList(Long id, RequestCartDto requestCartDto, HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;
        User user= (User) checkResponse.getData();


        Optional<Cart> cartOptional = cartRepository.findById(id);
        if (cartOptional.isEmpty()){
            return ResponseDto.fail("CART_NOT_FOUND", "생성된 장바구니가 없습니다");
        }

        Cart cart= cartOptional.orElseGet(null);
        cart.update(requestCartDto);

        return ResponseDto.success(cart);
    }

    @Override
    @Transactional
    public ResponseDto<?> removeOneProduct(Long productId, HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;

        Optional<Cart> cartOptional;
        try {
            cartOptional= cartRepository.findById(0L);
        }catch (Exception e){
            return ResponseDto.fail("ONE_DELETE_ERR", "1개의 상품 삭제 중 문제 발생");
        }

        Cart cart= cartOptional.orElseGet(null);

        cart.updateProductIds(productId);

        return ResponseDto.success(cart);
    }

    //        reset all
    @Override
    @Transactional
    public ResponseDto<?> deleteCartList(HttpServletRequest request) {
        ResponseDto<?> checkResponse= validateCheck(request);
        if(!checkResponse.isSuccess())
            return checkResponse;

        try {
            cartRepository.deleteAllInBatch();
        }catch (Exception e){
            return ResponseDto.fail("DELETE_ERR", "장바구니 삭제 중 문제 발생");
        }

        return ResponseDto.success("정상적으로 장바구니가 비워졌습니다");
    }



//    Util
    @Transactional
    public ResponseDto<?> validateCheck(HttpServletRequest request) {
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
        return tokenProvider.getUserFromAuthentication();
    }
}