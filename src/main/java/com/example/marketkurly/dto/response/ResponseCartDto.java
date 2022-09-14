package com.example.marketkurly.dto.response;

import com.example.marketkurly.model.Product;
import com.example.marketkurly.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCartDto {
    private Long id;
    private Long sum;
    private String address;

    private User user;
    private List<Product> productIds;
}