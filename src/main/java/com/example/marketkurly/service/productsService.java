package com.example.marketkurly.service;

import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.repository.ProductReposioty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class productsService {
    private ProductReposioty productRepository;
    @Transactional
    public ResponseDto<?> getProducts(HttpServletRequest request){
        try {
            var products= productRepository.findAll();
            return ResponseDto.success(products);
        } catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "전체 product 조희 실패");
        }
    }

    @Transactional
    public ResponseDto<?> getProduct(Long productId, HttpServletRequest request){
        try {
            var product= productRepository.findById(productId);
            return ResponseDto.success(product.orElseGet(null));
        } catch (Exception e){
            return ResponseDto.fail("RDS_ERR", e + "특정 product 조회 실패");
        }
    }
}
