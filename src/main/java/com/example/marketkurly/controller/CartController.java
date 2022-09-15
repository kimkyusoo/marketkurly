package com.example.marketkurly.controller;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //    전체 조회
    @RequestMapping(value = "/api/cart", method = RequestMethod.GET)
    public ResponseDto<?> getCartList(HttpServletRequest request){
        return cartService.getAllCartList(request);
    }

    //    장바구니 버튼 클릭 시 장바구니 객체 만들기
    @RequestMapping(value = "/api/cart", method = RequestMethod.POST)
    public ResponseDto<?> createCartList(@RequestBody RequestCartDto requestCartDto, HttpServletRequest request){
        return cartService.createCartList(requestCartDto, request);
    }

    //    장바구니 수정하기
    @RequestMapping(value = "/api/cart", method = RequestMethod.PUT)
    public ResponseDto<?> updateCartList(@RequestBody RequestCartDto requestCartDto, HttpServletRequest request){
        return cartService.updateCartList(requestCartDto, request);
    }

    //    장바구니 비우기
    @RequestMapping(value = "/api/cart", method = RequestMethod.DELETE)
    public ResponseDto<?> resetCartList(HttpServletRequest request){
        return cartService.deleteAllCartList(request);
    }

    //    장바구니에서 상품 하나 삭제하기
    @RequestMapping(value = "/api/cart/{productId}", method = RequestMethod.PATCH)
    public ResponseDto<?> toggleAddOrRemoveProduct(@PathVariable Long productId, HttpServletRequest request){
        return cartService.toggleProduct(productId, request);
    }

}