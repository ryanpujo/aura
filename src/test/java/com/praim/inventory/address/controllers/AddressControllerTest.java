package com.praim.inventory.address.controllers;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    private final static AddressDTO addressDTO = new AddressDTO();

    private static Address address;

    private final String exLocation = String.format("http://localhost/addresses/%d", 1);
    @BeforeEach
    void setUp() {
        addressDTO.setAddressLine("destination address");
        addressDTO.setPostalCode(13630);
        addressDTO.setCity("Jakarta Timur");
        addressDTO.setStateProvince("Jakarta");
        addressDTO.setCountry("Indonesia");
        address = AddressMapper.INSTANCE.toAddress(addressDTO);
        address.setId(1L);
    }

    @Test
    public void givenAddressDTO_WhenCreate_ShouldReturn() throws Exception {
        when(addressService.save(any())).thenReturn(address);

        String addressJSON = """
                    {
                      "address_line" : "destination address",
                      "postal_code" : 13630,
                      "city" : "Jakarta Timur",
                      "state_province" : "Jakarta",
                      "country" : "Indonesia"
                    }
                """;
        mockMvc.perform(post("/addresses")
                .content(addressJSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", exLocation));
    }
}
