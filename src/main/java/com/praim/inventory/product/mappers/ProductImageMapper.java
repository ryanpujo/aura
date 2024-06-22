package com.praim.inventory.product.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.praim.inventory.product.dtos.ProductImageDTO;
import com.praim.inventory.product.entities.ProductImage;

// Mapper Interface
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {
    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class); 

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductImage toEntity(ProductImageDTO imageDTO);
    ProductImageDTO toDTO(ProductImage productImage);

    List<ProductImage> toEntityList(List<ProductImageDTO> imageDTOs);
    List<ProductImageDTO> toDTOList(List<ProductImage> imageDTOs);
}
