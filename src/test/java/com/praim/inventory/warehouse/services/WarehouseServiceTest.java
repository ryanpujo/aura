package com.praim.inventory.warehouse.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.services.AddressService;
import com.praim.inventory.warehouse.WarehouseStatus;
import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import com.praim.inventory.warehouse.entities.Warehouse;
import com.praim.inventory.warehouse.mappers.WarehouseMapper;
import com.praim.inventory.warehouse.repositories.WarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private AddressService addressService;

    @Mock
    private WarehouseRepo warehouseRepo;

    @InjectMocks
    private WarehouseService warehouseService;

    private final WarehouseDTO warehouseDTO = new WarehouseDTO();
    private final AddressDTO addressDTO = new AddressDTO();
    private Address address;

    private Warehouse warehouse;

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
        warehouse = WarehouseMapper.INSTANCE.toWarehouse(warehouseDTO);
    }

    @Test
    public void givenWarehouseDTO_WhenSave_ThenReturnWarehouse() {
        when(warehouseRepo.save(any())).thenReturn(warehouse);

        var actual = warehouseService.createWarehouse(warehouseDTO);

        assertEquals(warehouse, actual);
        assertEquals(address, actual.getAddress());

        verify(addressService, times(1)).checkAddressDetailsAvailability(address, addressDTO);
        verify(warehouseRepo, times(1)).save(warehouse);
    }

}
