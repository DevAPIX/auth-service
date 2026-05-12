package com.devapix.auth_service.service;

import com.devapix.auth_service.exception.UserNotFoundException;
import com.devapix.auth_service.model.User;
import com.devapix.auth_service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("user.not.found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
        .password(user.getPassword()).roles(user.getRole().name()).build();
    }

}
