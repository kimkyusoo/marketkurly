package com.example.marketkurly.service;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;
import com.example.marketkurly.service.impl.CartServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class CartService implements CartServiceImpl {


    @Override
    @Transactional
    public ArrayList<Product> getAllCartList(HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<?> addCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<?> updateCartList(RequestCartDto requestCartDto, HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<?> deleteCartList(HttpServletRequest request) {
        return null;
    }

    @Override
    @Transactional
    public ResponseDto<?> validateCheck(HttpServletRequest request) {
        return null;
    }
}
