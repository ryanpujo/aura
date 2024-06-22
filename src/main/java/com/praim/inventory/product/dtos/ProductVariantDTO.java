package com.praim.inventory.product.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class ProductVariantDTO {

    @NotNull(message = "size is required")
    private String size;

    @NotNull(message = "color is required")
    private String color;

    @JsonProperty("additional_price")
    private BigDecimal additionalPrice;

    @NotNull(message = "stock is required")
    @Size(min = 1, message = "stock should be greater than 0")
    private int stock;
}
