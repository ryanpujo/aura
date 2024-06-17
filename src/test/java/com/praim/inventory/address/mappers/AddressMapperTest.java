package com.praim.inventory.address.mappers;


import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.mapper.AddressMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {

    private AddressDTO addressDTO = new AddressDTO();

    @BeforeEach
    void setUp() {
        addressDTO.setAddressLine("jl. mayjen sutoyo");
        addressDTO.setPostalCode(13630);
        addressDTO.setCity("Jakarta Timur");
        addressDTO.setStateProvince("Jakarta");
        addressDTO.setCountry("Indonesia");
    }

    @Test
    public void toAddressTest() {
        var actual = AddressMapper.INSTANCE.toAddress(addressDTO);

        assertEquals(addressDTO.getCountry(), actual.getCity().getStateProvince().getCountry().getName());
        assertEquals(addressDTO.getStateProvince(), actual.getCity().getStateProvince().getName());
        assertEquals(addressDTO.getCity(), actual.getCity().getName());
    }
}
