package com.praim.inventory.warehouse.entities;


import com.praim.inventory.address.entities.Address;
import com.praim.inventory.warehouse.WarehouseStatus;
import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "warehouses")
@Data
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(nullable = false)
    private WarehouseStatus status;
}
