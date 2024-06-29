package com.praim.inventory.product.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.entities.Product;

// Mapper Interface
@Mapper(uses = {ProductCategoryMapper.class, ProductImageMapper.class, ProductInventoryMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "images", source = "images")
    ProductDTO toDTO(Product product);
}

