package com.praim.inventory.user.mappers;


import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User toUser(UserDTO user);
}
