package com.praim.inventory.product.mappers;

import com.praim.inventory.product.dtos.ProductInventoryDTO;
import com.praim.inventory.product.entities.ProductInventory;
import com.praim.inventory.warehouse.mappers.WarehouseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ProductVariantMapper.class, WarehouseMapper.class})
public interface ProductInventoryMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductInventory toProductInventory(ProductInventoryDTO dto);

    ProductInventoryDTO toProductInventoryDTO(ProductInventory productInventory);
}
