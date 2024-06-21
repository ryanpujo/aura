package com.praim.inventory.warehouse.services;

import com.praim.inventory.address.repositories.AddressRepo;
import com.praim.inventory.address.services.AddressService;
import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import com.praim.inventory.warehouse.entities.Warehouse;
import com.praim.inventory.warehouse.mappers.WarehouseMapper;
import com.praim.inventory.warehouse.repositories.WarehouseRepo;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    private final WarehouseRepo warehouseRepo;
    private final AddressService addressService;

    public WarehouseService(WarehouseRepo warehouseRepo, AddressService addressService) {
        this.warehouseRepo = warehouseRepo;
        this.addressService = addressService;
    }

    public Warehouse createWarehouse(WarehouseDTO warehouseDTO) {
        var warehouse = WarehouseMapper.INSTANCE.toWarehouse(warehouseDTO);
        addressService.checkAddressDetailsAvailability(warehouse.getAddress(), warehouseDTO.getAddressDTO());
        return warehouseRepo.save(warehouse);
    }
}
