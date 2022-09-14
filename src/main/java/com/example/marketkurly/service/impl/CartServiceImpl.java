package com.example.marketkurly.service.impl;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public interface CartServiceImpl {
    public ResponseDto<?> createCartList(RequestCartDto requestCartDto, HttpServletRequest request);
    public ResponseDto<?> getAllCartList(HttpServletRequest request);
    public ResponseDto<?> updateCartList(Long id, RequestCartDto requestCartDto, HttpServletRequest request);
    public ResponseDto<?> removeOneProduct(Long productId, HttpServletRequest request);
    public ResponseDto<?> deleteCartList(HttpServletRequest request);
}