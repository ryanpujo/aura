package com.praim.inventory.warehouse.controllers;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.warehouse.WarehouseStatus;
import com.praim.inventory.warehouse.dtos.WarehouseDTO;
import com.praim.inventory.warehouse.entities.Warehouse;
import com.praim.inventory.warehouse.mappers.WarehouseMapper;
import com.praim.inventory.warehouse.services.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarehouseController.class)
@AutoConfigureMockMvc
public class WarehouseControllerTest {

    @MockBean
    private WarehouseService warehouseService;

    @Autowired
    private MockMvc mockMvc;

    private final WarehouseDTO warehouseDTO = new WarehouseDTO();
    private final AddressDTO addressDTO = new AddressDTO();

    private Warehouse warehouse;

    @BeforeEach
    public void setUp() {
        warehouseDTO.setName("gudang garam");
        warehouseDTO.setStatus(WarehouseStatus.OPERATIONAL);
        addressDTO.setCountry("Indonesia");
        addressDTO.setStateProvince("Jakarta");
        addressDTO.setCity("Jakarta Timur");
        addressDTO.setAddressLine("jl. mayjen sutoyo");
        addressDTO.setPostalCode(13630);

        warehouseDTO.setAddress(addressDTO);
        warehouse = WarehouseMapper.INSTANCE.toWarehouse(warehouseDTO);
        warehouse.setId(1L);
    }

    @Test
    public void givenJson_WhenCreateWarehouse_ShouldReturnLocationUri() throws Exception {
        when(warehouseService.createWarehouse(any())).thenReturn(warehouse);

        String json = """
                    {
                        "name" : "gudang garam",
                        "status" : "OPERATIONAL",
                        "address" : {
                            "address_line": "jl. mayjen sutoyo",
                            "postal_code": 13630,
                            "city": "Jakarta Timur",
                            "state_province": "Jakarta",
                            "country": "Indonesia"
                        }
                    }
                """;
        mockMvc.perform(post("/warehouses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/warehouses/1"));

        verify(warehouseService, times(1)).createWarehouse(warehouseDTO);
    }
}
