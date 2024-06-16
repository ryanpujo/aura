package com.praim.inventory.address.mapper;

import com.praim.inventory.address.dtos.AddressDTO;
import com.praim.inventory.address.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CityMapper.class})
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(source = "cityDTO", target = "city")
    @Mapping(target = "id", ignore = true)
    Address toAddress(AddressDTO addressDTO);
}
