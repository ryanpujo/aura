package com.praim.inventory.product.mappers;

import com.praim.inventory.product.dtos.ProductCategoryDTO;
import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.dtos.ProductImageDTO;
import com.praim.inventory.product.dtos.ProductVariantDTO;
import com.praim.inventory.product.entities.ProductImage;
import com.praim.inventory.product.entities.ProductVariant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {

    private ProductDTO productDTO = new ProductDTO();

    private ProductVariantDTO productVariantDTO = ProductVariantDTO.builder()
            .color("Black Mate").size("50x50").stock(50).additionalPrice(new BigDecimal(300))
            .build();

    private ProductImageDTO productImageDTO = new ProductImageDTO();

    private ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();

    private List<ProductVariant> productVariants;

    private List<ProductImage> productImages;

    @BeforeEach
    public void setUp() {
        productDTO.setName("MSI Bravo");
        productDTO.setImageUrl("url image");
        productDTO.setPrice(new BigDecimal(90));
        productDTO.setDescription("gaming laptop");
        productDTO.setVariants(List.of(productVariantDTO));
        productDTO.setImages(List.of(productImageDTO));
        productDTO.setCategories(List.of(productCategoryDTO));

        productVariants = ProductVariantMapper.INSTANCE.toVariants(productDTO.getVariants());
        productImages = ProductImageMapper.INSTANCE.toEntityList(productDTO.getImages());
    }

    @Test
    public void productMapperTest() {
        var actual = ProductMapper.INSTANCE.toEntity(productDTO);

        assertEquals(productDTO.getName(), actual.getName());
        assertEquals(productDTO.getDescription(), actual.getDescription());
        assertEquals(productVariants, actual.getProductVariants());
        assertEquals(productImages, actual.getImages());
    }
}
