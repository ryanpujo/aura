package com.praim.inventory.user.controllers;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.User;
import com.praim.inventory.user.mappers.UserMapper;
import com.praim.inventory.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final UserDTO dto = UserDTO.builder()
            .firstName("John").lastName("Doe")
            .password("password123").email("johndoe@example.com").build();

    private final User user = UserMapper.INSTANCE.toUser(dto);

    @BeforeEach
    public void setUp() {
        user.setId(1);
    }

    @Test
    public void givenUserJson_whenCreateUser_thenReturnUserLocation() throws Exception {
        when(userService.save(dto)).thenReturn(user);

        String json = """
                    {
                      "first_name": "John",
                      "last_name": "Doe",
                      "email": "johndoe@example.com",
                      "password": "password123"
                    }
                """;
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/1"));
    }
}
