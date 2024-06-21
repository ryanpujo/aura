package com.praim.inventory.warehouse.dtos;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.warehouse.WarehouseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WarehouseDTO {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "status is required")
    private WarehouseStatus status;

    @NotNull(message = "address is required")
    private AddressDTO addressDTO;
}
