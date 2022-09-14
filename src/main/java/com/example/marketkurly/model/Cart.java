package com.example.marketkurly.model;

import com.example.marketkurly.dto.request.RequestCartDto;
import com.example.marketkurly.dto.response.ResponseCartDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @ElementCollection
    private List<Long> productIds;
    
    public Cart(RequestCartDto chartDto) {
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