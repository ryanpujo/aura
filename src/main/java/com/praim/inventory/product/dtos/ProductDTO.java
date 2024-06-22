package com.praim.inventory.product.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ProductDTO {
  @NotNull(message = "Product name is required")
  @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
  private String name;

  @Size(max = 500, message = "Product description must not exceed 500 characters")
  private String description;

  @NotNull(message = "Product price is required")
  @DecimalMin(value = "0.01", message = "Price must be greater than zero")
  private BigDecimal price;

  @NotNull(message = "variant is required")
  private List<ProductVariantDTO> variants;

  @JsonProperty("image_url")
  @NotNull(message = "main image is required")
  private String imageUrl; // Main image URL

  @NotNull(message = "images is required")
  private List<ProductImageDTO> images; // List of image DTOs

  @NotNull(message = "categories is required")
  private List<ProductCategoryDTO> categories; // List of category DTOs
}

