package com.praim.inventory.address.mapper;

import com.praim.inventory.address.dtos.CityDTO;
import com.praim.inventory.address.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {StateProvinceMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "stateProvinceDTO", target = "stateProvince")
    City toCity(CityDTO cityDTO);
}
