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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private static AddressDTO addressDTO = new AddressDTO();

    private static CityDTO cityDTO = new CityDTO();

    private static StateProvinceDTO stateProvinceDTO = new StateProvinceDTO();

    private static CountryDTO countryDTO  = new CountryDTO();

    private static Address address;

    static void setUp() {
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

    static Stream<Arguments> sourceSaveMethod() {
        setUp();
        return Stream.of(
                Arguments.of(address, null),
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceSaveMethod")
    void givenAddress_WhenSave_ShouldReturn(Address exAddress, Class<? extends Exception> exception) {
        when(mockAddressRepo.save(any())).thenAnswer(invocation -> {
            if (exAddress == null) {
                throw new IllegalArgumentException("failed to create");
            }
            return exAddress;
        });

        if (exception == null) {
            var actual = addressService.save(addressDTO);

            assertEquals(address, actual);
            Mockito.verify(mockAddressRepo, times(1)).save(address);
            Mockito.verify(mockCountryRepo, times(1)).findByName(countryDTO.getName());
        } else {
            var result = assertThrows(exception, () -> addressService.save(addressDTO));
            assertEquals("failed to create", result.getMessage());
        }
    }
}
