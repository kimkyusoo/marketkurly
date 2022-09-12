package com.example.marketkurly.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCartDto {
    private Long id;
    private String title;
    private String imgURL;
    private String price;
    private String sum;
    private String address;
    private ArrayList<Product> productIds;
}
