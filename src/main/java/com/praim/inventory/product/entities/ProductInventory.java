package com.praim.inventory.product.entities;

import com.praim.inventory.warehouse.entities.Warehouse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants;

    public int getTotalStock() {
        return productVariants.stream().mapToInt(ProductVariant::getStock).sum();
    }
}
