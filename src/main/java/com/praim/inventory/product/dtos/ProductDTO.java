package com.praim.inventory.product.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private long id;

  @NotNull(message = "Product name is required")
  @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
  private String name;

  @Size(max = 500, message = "Product description must not exceed 500 characters")
  private String description;

  @NotNull(message = "Product price is required")
  @DecimalMin(value = "0.01", message = "Price must be greater than zero")
  private BigDecimal price;

  @NotNull(message = "sku is required")
  private String SKU;

  @NotNull(message = "variant is required")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<ProductVariantDTO> variants;

  private Set<ProductInventoryDTO> inventories;

  @JsonProperty("image_url")
  @NotNull(message = "main image is required")
  private String imageUrl; // Main image URL

  @NotNull(message = "images is required")
  private List<ProductImageDTO> images; // List of image DTOs

  @NotNull(message = "categories is required")
  private List<ProductCategoryDTO> categories; // List of category DTOs
}

