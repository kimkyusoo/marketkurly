package com.example.marketkurly.service.impl;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface CartServiceImpl {
    ArrayList<Product> getAllCartList();
    ResponseDto<?> addCartList(RequestCartDto requestCartDto, HttpServletRequest request);

    @Transactional
    ResponseDto<?> getAllCartList(HttpServletRequest request);

    @Transactional
    ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request);

    ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    ResponseDto<?> deleteCartList(HttpServletRequest request);
}
