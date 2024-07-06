package com.praim.inventory.config;

import com.praim.inventory.user.repositories.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        var authorities = List.of(new SimpleGrantedAuthority(user.get().getRole().name()));
        return new User(user.get().getEmail(), user.get().getPasswordHash(), authorities);
    }
}
