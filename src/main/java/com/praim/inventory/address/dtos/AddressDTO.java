package com.praim.inventory.address.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {

    @NotNull(message = "address line is required")
    @Size(min = 5, message = "address line should be at least 3 characters")
    private String addressLine;

    @NotNull(message = "postal code is required")
    private long postalCode;

    private CityDTO cityDTO;
}