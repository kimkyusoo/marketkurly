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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartServiceImpl{

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductReposioty productRepository;
    private TokenProvider tokenProvider;

    @Override
    @Transactional
    public ResponseDto<?> getAllCartList(HttpServletRequest request) {
//        ResponseDto<?> checkResponse= validateCheck(request);
//        if(!checkResponse.isSuccess())
//            return checkResponse;

        Cart cart;
        try {
            var cartOptional= cartRepository.findAllBy();
            cart= cartOptional.orElseGet(null);
        } catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "전체 조회 중 cart parsing err");
        }

        List<Long> productIds= cart.getProductIds();

        ArrayList<Product> productArrayList= new ArrayList<>();
        try {
            for (var id: productIds){
                productArrayList.add(productRepository.findById(id).orElseGet(null));
            }
        }catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "전체 조회 중 product-product parsing err");
        }

        return ResponseDto.success(productArrayList);
    }

    @Override
    @Transactional
    public ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
//        ResponseDto<?> checkResponse= validateCheck(request);
//        if(!checkResponse.isSuccess())
//            return checkResponse;

        Cart cart= new Cart(requestCartDto);

        List<Product> productArrayList= new ArrayList<>();
        try {
            var cartInfo= cartRepository.findAllBy().orElseGet(null);

            try {
                for (var id: cartInfo.getProductIds()){
                    productArrayList.add(productRepository.findById(id).orElseGet(null));
                }
            }catch (Exception e){
                return ResponseDto.fail("RDS_ERR", e + "productRepository parsing err");
            }
        } catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "cartRepository parsing err");
        }

        Long sum= calculateAllPrice(productArrayList);
        cart.updateSumPrice(sum);

        cartRepository.save(cart);

//        @todo 수량은 추가 예정
        return ResponseDto.success(
                ResponseCartDto.builder()
                        .products(productArrayList)
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
    public ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
//        ResponseDto<?> checkResponse= validateCheck(request);
//        if(!checkResponse.isSuccess())
//            return checkResponse;

        List<Cart> cartList;
        try {
            cartList = cartRepository.findAll();
        }catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "cartRepository parsing err");
        }

        if (cartList.isEmpty()){
            return ResponseDto.fail("CART_NOT_FOUND", "생성된 장바구니가 없습니다");
        }

//        @todo 잘 모르겠는데... 꼬였다.
        int cartSize= cartList.size();
        Cart cart= (cartSize == 1) ? cartList.get(0) : cartList.get(cartSize-1);
        cart.update(requestCartDto);

        return ResponseDto.success(cart);
    }


    @Override
    @Transactional
    public ResponseDto<?> deleteAllCartList(HttpServletRequest request) {
//        ResponseDto<?> checkResponse= validateCheck(request);
//        if(!checkResponse.isSuccess())
//            return checkResponse;

        try {
            cartRepository.deleteAllInBatch();
        }catch (Exception e){
            return ResponseDto.fail("DELETE_ERR", "장바구니 삭제 중 문제 발생");
        }

        return ResponseDto.success("정상적으로 장바구니가 비워졌습니다");
    }

    @Override
    @Transactional
    public ResponseDto<?> removeOneProduct(Long productId, HttpServletRequest request) {
//        ResponseDto<?> checkResponse= validateCheck(request);
//        if(!checkResponse.isSuccess())
//            return checkResponse;

        List<Cart> cartList;
        try {
            cartList = cartRepository.findAll();
        }catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "cartRepository parsing err");
        }

        if (cartList.isEmpty()){
            return ResponseDto.fail("CART_NOT_FOUND", "생성된 장바구니가 없습니다");
        }

//        @todo 잘 모르겠는데... 꼬였다.
        int cartSize= cartList.size();
        Cart cart= (cartSize == 1) ? cartList.get(0) : cartList.get(cartSize-1);

        cart.updateProductIds(productId);

        return ResponseDto.success(cart);
    }

    
//    User 인증
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




