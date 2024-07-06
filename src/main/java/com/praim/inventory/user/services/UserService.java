package com.praim.inventory.user.services;

import com.praim.inventory.user.dtos.UserDTO;
import com.praim.inventory.user.entities.User;
import com.praim.inventory.user.mappers.UserMapper;
import com.praim.inventory.user.repositories.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepository;

    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDTO dto) {
        User user = UserMapper.INSTANCE.toUser(dto);
        return userRepository.save(user);
    }
}
