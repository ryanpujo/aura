package com.praim.inventory.product.repositories;

import com.praim.inventory.product.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInventoryRepo extends JpaRepository<ProductInventory, Long> {
}
