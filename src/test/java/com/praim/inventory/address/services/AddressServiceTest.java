package com.praim.inventory.address.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.dtos.CityDTO;
import com.praim.inventory.address.dtos.CountryDTO;
import com.praim.inventory.address.dtos.StateProvinceDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.repositories.AddressRepo;
import com.praim.inventory.address.repositories.CityRepo;
import com.praim.inventory.address.repositories.CountryRepo;
import com.praim.inventory.address.repositories.StateProvinceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private CountryRepo mockCountryRepo;

    @Mock
    private StateProvinceRepo mockStateProvinceRepo;

    @Mock
    private CityRepo mockCityRepo;

    @Mock
    private AddressRepo mockAddressRepo;

    @InjectMocks
    private AddressService addressService;

    private AddressDTO addressDTO = new AddressDTO();

    private CityDTO cityDTO = new CityDTO();

    private StateProvinceDTO stateProvinceDTO = new StateProvinceDTO();

    private CountryDTO countryDTO  = new CountryDTO();

    private Address address;

    @BeforeEach
    void setUp() {
        countryDTO.setName("Indonesia");
        stateProvinceDTO.setName("Jakarta");
        stateProvinceDTO.setCountryDTO(countryDTO);
        cityDTO.setName("Jakarta Timur");
        cityDTO.setStateProvinceDTO(stateProvinceDTO);
        addressDTO.setAddressLine("destination address");
        addressDTO.setPostalCode(13630);
        addressDTO.setCityDTO(cityDTO);
        address = AddressMapper.INSTANCE.toAddress(addressDTO);
    }

    @Test
    void givenAddress_WhenSave_ShouldReturn() {
        when(mockAddressRepo.save(any())).thenReturn(address);

        var actual = addressService.save(addressDTO);
        System.out.println(actual);

        assertEquals(address, actual);
        Mockito.verify(mockAddressRepo, times(1)).save(address);
        Mockito.verify(mockCountryRepo, times(1)).findByName(countryDTO.getName());
    }
}
