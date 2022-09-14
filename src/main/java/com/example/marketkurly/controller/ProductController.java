package com.example.marketkurly.controller;

import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.service.productsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public class ProductController {
    productsService productsService;
    //    전체 조회
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseDto<?> getAllProducts(HttpServletRequest request){
        return productsService.getProducts(request);
    }

    @RequestMapping(value = "/api/products{productId}", method = RequestMethod.GET)
    public ResponseDto<?> getOneProduct(@PathVariable Long productId, HttpServletRequest request){
        return productsService.getProduct(productId, request);
    }
}
