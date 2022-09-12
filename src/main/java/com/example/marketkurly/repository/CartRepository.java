package com.example.marketkurly.repository;

import com.example.marketkurly.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
