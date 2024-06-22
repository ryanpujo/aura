package com.praim.inventory.product.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.praim.inventory.product.dtos.ProductCategoryDTO;
import com.praim.inventory.product.entities.ProductCategory;

// Mapper Interface
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductCategoryMapper {
    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class); 

    @Mapping(target = "id", ignore = true)
    ProductCategory toEntity(ProductCategoryDTO productDTO);

    ProductCategoryDTO toDTO(ProductCategory productCategory);

    List<ProductCategory> toEntityList(List<ProductCategoryDTO> categoryDTOs);
    List<ProductCategoryDTO> toDTOList(List<ProductCategory> categoryDTOs);
}
