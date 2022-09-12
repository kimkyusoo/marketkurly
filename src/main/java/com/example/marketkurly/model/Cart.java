package com.example.marketkurly.model;

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


    @Column(nullable = false)
    private ArrayList<Product> productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imgURL;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String sum;

    @Column(nullable = false)
    private String address;


    public Cart(ResponseCartDto chartDto) {
        this.title = chartDto.getTitle();
        this.imgURL = chartDto.getImgURL();
        this.price = chartDto.getPrice();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productId= chartDto.getProductIds();
    }

    public void update(ResponseCartDto chartDto){
        this.title = chartDto.getTitle();
        this.imgURL = chartDto.getImgURL();
        this.price = chartDto.getPrice();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productId= chartDto.getProductIds();
    }
}
