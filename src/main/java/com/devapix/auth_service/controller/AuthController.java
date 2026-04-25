package com.devapix.auth_service.controller;

import com.devapix.auth_service.dto.LoginRequest;
import com.devapix.auth_service.dto.RegisterRequest;
import com.devapix.auth_service.dto.response.DeleteResponse;
import com.devapix.auth_service.dto.response.LoginResponse;
import com.devapix.auth_service.dto.response.RegisterResponse;
import com.devapix.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("API called: /auth/register");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("API called: /auth/login");
        return ResponseEntity.ok(authService.login(request));
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<DeleteResponse> deleteMyAccount(Authentication authentication) {
        log.info("API called: /auth/users/me");
        String email = authentication.getName();
        return ResponseEntity.ok(authService.deleteAccount(email));
    }
}