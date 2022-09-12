package com.example.marketkurly.repository;

import com.example.marketkurly.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CartRepository extends JpaRepository<Cart, Long> {
    ArrayList<Product> findAllByProductId();

}
