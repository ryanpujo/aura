package com.praim.inventory.product.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductImageDTO {
  @NotNull(message = "Image URL is required")
  private String imageUrl;

  // Optional:
  // @Min(value = 0, message = "Display order must be zero or greater")
  // private int displayOrder;
}

