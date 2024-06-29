package com.praim.inventory.warehouse.mappers;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.warehouse.WarehouseStatus;
import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehouseMapperTest {

    private final WarehouseDTO warehouseDTO = new WarehouseDTO();
    private final AddressDTO addressDTO = new AddressDTO();
    private Address address;

    @BeforeEach
    public void setUp() {
        warehouseDTO.setName("gudang garam");
        warehouseDTO.setStatus(WarehouseStatus.OPERATIONAL);
        addressDTO.setCountry("Indonesia");
        addressDTO.setStateProvince("Jakarta");
        addressDTO.setCity("Jakarta Timur");
        addressDTO.setAddressLine("jl. mayjen sutoyo");
        addressDTO.setPostalCode(13360);

        warehouseDTO.setAddress(addressDTO);
        address = AddressMapper.INSTANCE.toAddress(addressDTO);
    }

    @Test
    public void givenWarehouseDTO_WhenMapping_ShouldReturnWarehouse() {
        var actual = WarehouseMapper.INSTANCE.toWarehouse(warehouseDTO);

        assertEquals(warehouseDTO.getName(), actual.getName());
        assertEquals(warehouseDTO.getStatus(), actual.getStatus());
        assertEquals(address, actual.getAddress());
    }
}
