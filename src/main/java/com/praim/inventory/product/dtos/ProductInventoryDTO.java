package com.praim.inventory.product.dtos;

import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProductInventoryDTO {
    private int totalStock;
    private List<ProductVariantDTO> productVariants;
    private WarehouseDTO warehouse;
}
