package com.devapix.auth_service.service.impl;

import com.devapix.auth_service.dto.LoginRequest;
import com.devapix.auth_service.dto.RegisterRequest;
import com.devapix.auth_service.dto.response.DeleteResponse;
import com.devapix.auth_service.dto.response.LoginResponse;
import com.devapix.auth_service.dto.response.RegisterResponse;
import com.devapix.auth_service.enums.Role;
import com.devapix.auth_service.exception.UserAlreadyExistsException;
import com.devapix.auth_service.exception.UserNotFoundException;
import com.devapix.auth_service.model.User;
import com.devapix.auth_service.repository.UserRepo;
import com.devapix.auth_service.service.AuthService;
import com.devapix.auth_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MessageSource messageSource;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        log.info("Register request received for email: {}", request.getEmail());
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("user.already.exists");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DEVELOPER);
        userRepo.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getUserid(), user.getRole().name());
        log.info("User registered successfully: {}", user.getEmail());
        return new RegisterResponse(token, user.getEmail(), user.getRole().name());
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        log.info("Login attempt for email: {}", request.getEmail());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> {
            log.error("User not found: {}", request.getEmail());
            return new UserNotFoundException("user.not.found");
        });
        String token = jwtService.generateToken(user.getEmail(), user.getUserid(), user.getRole().name());
        log.info("Login successful for email: {}", user.getEmail());
        return new LoginResponse(token, user.getEmail(), user.getRole().name());
    }

    @Override
    public DeleteResponse deleteAccount(String email) {

        log.info("Delete account request for email: {}", email);
        User user = userRepo.findByEmail(email).orElseThrow(() -> {
            log.error("User not found for deletion: {}", email);
            return new UserNotFoundException("user.not.found");
        });
        userRepo.delete(user);
        String message = messageSource.getMessage("account.deleted", null, Locale.getDefault());
        log.info("Account deleted successfully: {}", email);
        return new DeleteResponse(message);
    }

    @Override
    public boolean isUserValid(Integer userId) {
        return userRepo.findById(userId).isPresent();
    }
}