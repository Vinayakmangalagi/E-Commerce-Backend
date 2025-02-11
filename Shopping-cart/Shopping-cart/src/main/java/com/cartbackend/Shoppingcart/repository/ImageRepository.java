package com.cartbackend.Shoppingcart.repository;

import com.cartbackend.Shoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
//    static List<Image> findByProductId(Long id) {
//    }
}
