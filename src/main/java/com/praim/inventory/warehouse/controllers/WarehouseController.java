package com.praim.inventory.warehouse.controllers;

import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import com.praim.inventory.warehouse.services.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<Void> createWarehouse(@Valid @RequestBody WarehouseDTO warehouseDTO) {
        var warehouse = warehouseService.createWarehouse(warehouseDTO);
        var link = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(warehouse.getId())
                .toUri();
        return ResponseEntity.created(link).build();
    }
}
