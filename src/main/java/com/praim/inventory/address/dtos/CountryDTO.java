package com.praim.inventory.address.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CountryDTO {
    @NotNull(message = "name of the country is required")
    private String name;
}
