package com.example.marketkurly.model;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseCartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    private Long sum;

    @Column(nullable = false)
    private String address;

    @OneToOne (fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    private User user;
    @ManyToMany (mappedBy = "product_id", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> productIds;


    public Cart(ResponseCartDto chartDto) {
        this.user = chartDto.getUser();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productIds = chartDto.getProductIds();
    }

    public Cart(RequestCartDto chartDto) {
        this.user = chartDto.getUser();
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productIds = chartDto.getProductIds();
    }

    public void updateSumPrice(Long sum){
        this.sum= sum;
    }

    public void updateProductIds(Long selectedId){
        this.productIds.remove(selectedId);
    }

    public void update(RequestCartDto chartDto){
        this.sum = chartDto.getSum();
        this.address = chartDto.getAddress();
        this.productIds = chartDto.getProductIds();
    }
}