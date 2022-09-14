package com.example.marketkurly.service;

import com.example.marketkurly.dto.response.ProductResponseDto;
import com.example.marketkurly.dto.response.ResponseDto;
import com.example.marketkurly.model.Product;
import com.example.marketkurly.repository.ProductReposioty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductReposioty productRepository;

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllProducts() {
        List<Product> products = productRepository.findAllByOrderByModifiedAtDesc();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products)

            productResponseDtos.add(
                    ProductResponseDto.builder()
                            .id(product.getId())
                            .title(product.getTitle())
                            .imageUrl(product.getImageUrl())
                            .price(product.getPrice())
                            .modifiedAt(product.getModifiedAt())
                            .build()
            );

        return ResponseDto.success(productResponseDtos);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getProduct(Long id) {
        Product product = isPresentProduct(id);
        if (null == product) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 상품id 입니다.");
        }

        return ResponseDto.success(
                ProductResponseDto.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .imageUrl(product.getImageUrl())
                        .price(product.getPrice())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public Product isPresentProduct(Long id) {
        Optional<Product> optionalPost = productRepository.findById(id);
        return optionalPost.orElse(null);
    }


}