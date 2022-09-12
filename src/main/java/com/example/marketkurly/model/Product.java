package com.example.marketkurly.model;


import lombok.*;
import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;


    @Column(nullable = false)
    private String imageUrl;


    @Column(nullable = false)
    private int price;


    @Column(nullable = false)
    private String product_detail;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;


    public Product(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
