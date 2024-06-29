package com.praim.inventory.warehouse.entities;


import com.praim.inventory.address.entities.Address;
import com.praim.inventory.product.entities.ProductInventory;
import com.praim.inventory.warehouse.WarehouseStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;


@Entity(name = "warehouses")
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Address.class)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WarehouseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductInventory> inventories;
}
