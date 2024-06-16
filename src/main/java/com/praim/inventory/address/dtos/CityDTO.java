package com.praim.inventory.address.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CityDTO {

    @NotNull(message = "name is required")
    private String name;

    private StateProvinceDTO stateProvinceDTO;
}
