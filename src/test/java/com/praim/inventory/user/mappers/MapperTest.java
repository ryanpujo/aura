package com.praim.inventory.user.mappers;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    private final UserDTO dtoAdmin = UserDTO.builder().id(1)
            .firstName("Ryan").lastName("Pujo")
            .email("ryanpujo@gmail.com")
            .password("elenoirpraim").role(Role.ROLE_ADMIN).build();

    private final UserDTO dtoUser = UserDTO.builder()
            .firstName("Ryan").lastName("Pujo")
            .email("ryanpujo@gmail.com")
            .password("elenoirpraim").build();


    @DisplayName("test mapper to user")
    @Test
    public void givenDTO_whenMapUserDTOToUser_thenReturnUser() {
        var userAdmin = UserMapper.INSTANCE.toUser(dtoAdmin);
        var userUser = UserMapper.INSTANCE.toUser(dtoUser);

        assertEquals(dtoAdmin.getRole(), userAdmin.getRole());
        assertEquals(dtoAdmin.getFirstName(), userAdmin.getFirstName());
        assertEquals(dtoAdmin.getLastName(), userAdmin.getLastName());
        assertEquals(dtoAdmin.getEmail(), userAdmin.getEmail());
        assertEquals(dtoAdmin.getId(), userAdmin.getId());

        assertEquals(dtoUser.getRole(), userUser.getRole());
        assertEquals(dtoUser.getFirstName(), userUser.getFirstName());
        assertEquals(dtoUser.getLastName(), userUser.getLastName());
        assertEquals(dtoUser.getEmail(), userUser.getEmail());
    }
}
