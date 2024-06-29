package com.praim.inventory.address.mapper;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CityMapper.class})
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "city", target = "city.name")
    @Mapping(source = "stateProvince", target = "city.stateProvince.name")
    @Mapping(source = "country", target = "city.stateProvince.country.name")
    @Mapping(target = "id", ignore = true)
    Address toAddress(AddressDTO addressDTO);

    @Mapping(target = "stateProvince", source = "city.stateProvince.name")
    @Mapping(target = "country", source = "city.stateProvince.country.name")
    @Mapping(target = "city", source = "city.name")
    AddressDTO toAddressDTO(Address address);

}
