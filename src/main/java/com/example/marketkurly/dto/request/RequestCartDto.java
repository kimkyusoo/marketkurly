package com.example.marketkurly.dto.request;

import com.example.marketkurly.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCartDto {
    private Long id;
    private User user;
    private String price;
    private String sum;
    private String address;
    private ArrayList<Product> productIds;
}
