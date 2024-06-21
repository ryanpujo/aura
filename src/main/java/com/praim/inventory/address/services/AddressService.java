package com.praim.inventory.address.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.entities.City;
import com.praim.inventory.address.entities.Country;
import com.praim.inventory.address.entities.StateProvince;
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
        checkAddressDetailsAvailability(address, addressDTO);
        return addressRepo.save(address);
    }

    public Country checkCountryAddressAvailability(AddressDTO addressDTO) {
        return  countryRepo.findByName(addressDTO.getCountry()).orElseGet(() -> new Country(addressDTO.getCountry()));
    }

    public StateProvince checkStateProvinceAddressAvailability(AddressDTO addressDTO) {
        var country = checkCountryAddressAvailability(addressDTO);
        return stateProvinceRepo.findByNameAndCountryId(addressDTO.getStateProvince(), country.getId())
                .orElseGet(() -> new StateProvince(addressDTO.getStateProvince()));
    }

    public City checkCityAddressAvailability(AddressDTO addressDTO) {
        var stateProvince = checkStateProvinceAddressAvailability(addressDTO);

        return cityRepo.findByNameAndStateProvinceId(addressDTO.getCity(), stateProvince.getId())
                .orElseGet(() -> new City(addressDTO.getCity()));
    }

    public void checkAddressDetailsAvailability(Address address, AddressDTO addressDTO) {
        var country = countryRepo.findByName(addressDTO.getCountry())
                .orElseGet(() -> address.getCity().getStateProvince().getCountry());
        var stateProvince = stateProvinceRepo.findByNameAndCountryId(addressDTO.getStateProvince(), country.getId())
                .orElseGet(() -> address.getCity().getStateProvince());
        var city = cityRepo.findByNameAndStateProvinceId(addressDTO.getCity(), stateProvince.getId())
                .orElseGet(address::getCity);
        address.setCity(city);
        address.getCity().setStateProvince(stateProvince);
        address.getCity().getStateProvince().setCountry(country);
    }
}
