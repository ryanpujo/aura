package com.praim.inventory.user.services;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.Role;
import com.praim.inventory.user.entities.User;
import com.praim.inventory.user.mappers.UserMapper;
import com.praim.inventory.user.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    private final UserDTO dto = UserDTO.builder()
            .firstName("Ryan").lastName("Pujo")
            .email("ryanpujo@gmail.com").password("elenoirpraim")
            .role(Role.ROLE_ADMIN).build();

    private final User user = UserMapper.INSTANCE.toUser(dto);

    @Test
    public void givenUserDTO_whenSaveUser_thenReturnUser() {
        when(userRepo.save(user)).thenReturn(user);

        var actual = userService.save(dto);

        assertEquals(user, actual);
    }
}
