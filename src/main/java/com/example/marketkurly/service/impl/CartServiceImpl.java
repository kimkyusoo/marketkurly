package com.example.marketkurly.service.impl;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface CartServiceImpl {
    @Transactional
    ResponseDto<?> getAllCartList(HttpServletRequest request);
    @Transactional
    ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    @Transactional
    ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    @Transactional
    ResponseDto<?> deleteAllCartList(HttpServletRequest request);
    @Transactional
    ResponseDto<?> toggleProduct(Long productId, HttpServletRequest request);
}