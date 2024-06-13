package com.praim.inventory.product.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductCategoryDTO {
  @NotNull(message = "Category name is required")
  private String name;
}

