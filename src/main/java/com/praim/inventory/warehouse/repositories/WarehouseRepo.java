package com.praim.inventory.warehouse.repositories;

import com.praim.inventory.warehouse.entities.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
}
