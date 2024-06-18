package com.praim.inventory.address.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.repositories.AddressRepo;
import com.praim.inventory.address.repositories.CityRepo;
import com.praim.inventory.address.repositories.CountryRepo;
import com.praim.inventory.address.repositories.StateProvinceRepo;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepo addressRepo;
    private final CityRepo cityRepo;
    private final StateProvinceRepo stateProvinceRepo;
    private final CountryRepo countryRepo;


    /**
     * Constructs an AddressService with the necessary repositories.
     *
     * @param addressRepo         the repository for address data
     * @param cityRepo            the repository for city data
     * @param stateProvinceRepo   the repository for state/province data
     * @param countryRepo         the repository for country data
     */
    public AddressService(
            AddressRepo addressRepo,
            CityRepo cityRepo,
            StateProvinceRepo stateProvinceRepo,
            CountryRepo countryRepo
    ) {
        this.addressRepo = addressRepo;
        this.cityRepo = cityRepo;
        this.stateProvinceRepo = stateProvinceRepo;
        this.countryRepo = countryRepo;
    }

    /**
     * Saves an address to the repository.
     *
     * @param addressDTO the DTO containing address information
     * @return the saved Address entity
     */
    public Address save(AddressDTO addressDTO) {
        var address = AddressMapper.INSTANCE.toAddress(addressDTO);
        var country = countryRepo.findByName(addressDTO.getCountry())
                .orElseGet(() -> address.getCity().getStateProvince().getCountry());
        var statePRovince = stateProvinceRepo.findByNameAndCountryId(addressDTO.getStateProvince(), country.getId())
                .orElseGet(() -> address.getCity().getStateProvince());
        var city = cityRepo.findByNameAndStateProvinceId(addressDTO.getCity(), statePRovince.getId()).orElseGet(address::getCity);
        statePRovince.setCountry(country);
        city.setStateProvince(statePRovince);
        address.setCity(city);
        return addressRepo.save(address);
    }
}
