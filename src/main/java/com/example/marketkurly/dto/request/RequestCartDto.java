package com.example.marketkurly.dto.request;

import com.example.marketkurly.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCartDto {
    private ArrayList<Product> productId;
}
