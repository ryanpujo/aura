package com.praim.inventory.address.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StateProvinceDTO {
    @NotNull(message = "state name is required")
    private String name;

    private CountryDTO countryDTO;
}
