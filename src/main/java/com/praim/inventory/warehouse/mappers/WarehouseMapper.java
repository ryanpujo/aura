package com.praim.inventory.warehouse.mappers;

import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import com.praim.inventory.warehouse.entities.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AddressMapper.class)
public interface WarehouseMapper {

    WarehouseMapper INSTANCE = Mappers.getMapper(WarehouseMapper.class);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "inventories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Warehouse toWarehouse(WarehouseDTO warehouseDTO);

    WarehouseDTO toWarehouseDTO(Warehouse warehouse);

}
