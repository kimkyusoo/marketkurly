//package com.example.marketkurly.controller;
//
//import com.example.marketkurly.dto.response.ResponseDto;
//import com.example.marketkurly.service.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.http.HttpRequest;
//
//@RestController
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartService cartService;
//
////    전체 조회
//    @RequestMapping(value = "/api/cart", method = RequestMethod.GET)
//    public ResponseDto<?> getCartList(HttpRequest request){
//        return cartService.getAllCartList();
//    }
//
////    장바구니 버튼 클릭 시 장바구니 객체 만들기
//    @RequestMapping(value = "/api/cart", method = RequestMethod.POST)
//    public ResponseDto<?> getCartList(HttpRequest request){
//        return car
//    }
//
////    장바구니 수정하기
//    @RequestMapping(value = "/api/cart", method = RequestMethod.PUT)
//    public ResponseDto<?> getCartList(HttpRequest request){
//        return car
//    }
//
////    장바구니 비우기
//    @RequestMapping(value = "/api/cart", method = RequestMethod.DELETE)
//    public ResponseDto<?> getCartList(HttpRequest request){
//        return car
//    }
//}
