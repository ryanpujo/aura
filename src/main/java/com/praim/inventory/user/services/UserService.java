package com.praim.inventory.user.services;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.User;
import com.praim.inventory.user.mappers.UserMapper;
import com.praim.inventory.user.repositories.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserDTO dto) {
        User user = UserMapper.INSTANCE.toUser(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        return userRepository.save(user);
    }
}
