package com.praim.inventory.product.entities;

import com.praim.inventory.warehouse.entities.Warehouse;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product_inventory")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "warehouse_id"}))
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    @EqualsAndHashCode.Exclude
    private Warehouse warehouse;

    @Column(nullable = false)
    private int stock;
}
