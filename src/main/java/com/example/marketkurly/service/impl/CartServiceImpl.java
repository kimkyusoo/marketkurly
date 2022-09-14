package com.example.marketkurly.service.impl;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface CartServiceImpl {
    ResponseDto<?> getAllCartList(HttpServletRequest request);
    ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    ResponseDto<?> deleteAllCartList(HttpServletRequest request);
    ResponseDto<?> removeOneProduct(Long productId, HttpServletRequest request);
}