package com.praim.inventory.address.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.repositories.AddressRepo;
import com.praim.inventory.address.repositories.CityRepo;
import com.praim.inventory.address.repositories.CountryRepo;
import com.praim.inventory.address.repositories.StateProvinceRepo;
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

    private static Address address;

    static void setUp() {
        addressDTO.setAddressLine("destination address");
        addressDTO.setPostalCode(13630);
        addressDTO.setCity("Jakarta Timur");
        addressDTO.setStateProvince("Jakarta");
        addressDTO.setCountry("Indonesia");
        address = AddressMapper.INSTANCE.toAddress(addressDTO);
    }

    static Stream<Arguments> sourceSaveMethod() {
        setUp();
        return Stream.of(
                Arguments.of(addressDTO, null),
                Arguments.of(null, IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("sourceSaveMethod")
    void givenAddress_WhenSave_ShouldReturn(AddressDTO exArg, Class<? extends Exception> exception) {
        when(mockAddressRepo.save(any())).thenAnswer(invocation -> {
            if (exArg == null) {
                throw new IllegalArgumentException("failed to create");
            }
            return address;
        });

        if (exception == null) {
            var actual = addressService.save(exArg);

            assertEquals(address, actual);
            Mockito.verify(mockAddressRepo, times(1)).save(address);
            Mockito.verify(mockCountryRepo, times(1)).findByName(addressDTO.getCountry());
        } else {
            var result = assertThrows(exception, () -> addressService.save(addressDTO));
            assertEquals("failed to create", result.getMessage());
        }
    }
}
