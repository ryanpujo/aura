package com.praim.inventory.address.mapper;

import com.praim.inventory.address.dtos.StateProvinceDTO;
import com.praim.inventory.address.entities.StateProvince;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CountryMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StateProvinceMapper {
    StateProvinceMapper INSTANCEE = Mappers.getMapper(StateProvinceMapper.class);

    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "country", source = "countryDTO")
    @Mapping(target = "id", ignore = true)
    StateProvince toStateProvince(StateProvinceDTO stateProvinceDTO);
}
