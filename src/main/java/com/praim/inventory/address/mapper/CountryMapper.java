package com.praim.inventory.address.mapper;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.dtos.CountryDTO;
import com.praim.inventory.address.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CountryMapper {
    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stateProvinces", ignore = true)
    Country toCountry(CountryDTO countryDTO);
}
