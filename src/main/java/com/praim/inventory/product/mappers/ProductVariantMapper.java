package com.praim.inventory.product.mappers;

import com.praim.inventory.product.dtos.ProductVariantDTO;
import com.praim.inventory.product.entities.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductVariantMapper {

    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductVariant toVariant(ProductVariantDTO productVariantDTO);

    List<ProductVariant> toVariants(List<ProductVariantDTO> variantDTOS);
}
