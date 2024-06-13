package com.praim.inventory.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praim.inventory.product.entities.ProductImage;

public interface ProductImageRepo extends JpaRepository<ProductImage, Long> {

}
