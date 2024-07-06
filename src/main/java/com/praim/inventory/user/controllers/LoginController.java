package com.praim.inventory.user.controllers;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDTO dto) {
        var createdUser = userService.save(dto);
        var uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
