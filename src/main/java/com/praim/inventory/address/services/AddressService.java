package com.praim.inventory.address.services;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import com.praim.inventory.address.mapper.AddressMapper;
import com.praim.inventory.address.mapper.CityMapper;
import com.praim.inventory.address.mapper.CountryMapper;
import com.praim.inventory.address.mapper.StateProvinceMapper;
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

    public Address save(AddressDTO addressDTO) {
        var address = AddressMapper.INSTANCE.toAddress(addressDTO);
        var country = countryRepo.findByName(addressDTO.getCityDTO()
                .getStateProvinceDTO().getCountryDTO().getName())
                .orElseGet(() -> address.getCity().getStateProvince().getCountry());
        var statePRovince = stateProvinceRepo
                .findByName(addressDTO.getCityDTO().getStateProvinceDTO().getName())
                .orElseGet(() -> address.getCity().getStateProvince());
        var city = cityRepo.findByName(addressDTO.getCityDTO().getName())
                .orElseGet(address::getCity);
        statePRovince.setCountry(country);
        city.setStateProvince(statePRovince);
        address.setCity(city);
        return addressRepo.save(address);
    }
}
