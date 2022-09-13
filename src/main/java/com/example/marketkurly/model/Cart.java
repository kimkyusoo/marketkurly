package com.example.marketkurly.model;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseCartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User user;

    @Column(nullable = false)
    private ArrayList<Product> productId;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String sum;

    @Column(nullable = false)
    private String address;


    public Cart(ResponseCartDto chartDto) {
        this.user = chartDto.getUser();
        this.price = chartDto.getPrice();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productId= chartDto.getProductIds();
    }

    public Cart(RequestCartDto chartDto) {
        this.user = chartDto.getUser();
        this.price = chartDto.getPrice();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productId= chartDto.getProductIds();
    }

    public void update(RequestCartDto chartDto){
        this.price = chartDto.getPrice();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productId= chartDto.getProductIds();
    }
}
