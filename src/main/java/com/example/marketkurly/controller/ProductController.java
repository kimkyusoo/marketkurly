package com.example.marketkurly.controller;

import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductsService productsService;
    //    전체 상품 조회
    @RequestMapping(value = "/api/products", method = RequestMethod.GET)
    public ResponseDto<?> getAllProducts(){

        return productsService.getAllProducts();
    }
    //    단일 상품 조회
    @RequestMapping(value = "/api/product/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getProduct(@PathVariable Long id) {

        return productsService.getProduct(id);
    }
}