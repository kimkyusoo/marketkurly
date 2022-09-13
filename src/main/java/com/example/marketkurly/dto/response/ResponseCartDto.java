package com.example.marketkurly.dto.response;

import com.example.marketkurly.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCartDto {
    private Long id;
    private User user;
    private String price;
    private String sum;
    private String address;
    private ArrayList<Product> productIds;
}
