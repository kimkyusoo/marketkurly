package com.example.marketkurly.service.impl;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface CartServiceImpl {
    ArrayList<Product> getAllCartList(HttpServletRequest request);
    ResponseDto<?> addCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    ResponseDto<?> deleteCartList(HttpServletRequest request);
    ResponseDto<?> validateCheck(HttpServletRequest request);
}
