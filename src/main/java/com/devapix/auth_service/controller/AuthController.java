package com.devapix.auth_service.controller;


import  java.util.*;
import com.devapix.auth_service.dto.LoginRequest;
import com.devapix.auth_service.dto.RegisterRequest;
import com.devapix.auth_service.dto.response.DeleteResponse;
import com.devapix.auth_service.dto.response.LoginResponse;
import com.devapix.auth_service.dto.response.RegisterResponse;
import com.devapix.auth_service.dto.response.UserInfoResponse;
import com.devapix.auth_service.model.User;
import com.devapix.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.stream.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("API called: /auth/register");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("API called: /auth/login");
        return ResponseEntity.ok(authService.login(request));
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<DeleteResponse> deleteMyAccount(@Parameter(hidden = true) @RequestHeader("X-User-Id") String userIdStr) {
        Integer userId = Integer.parseInt(userIdStr);
        log.info("API called: /auth/users/me for userId: {}", userId);
        return ResponseEntity.ok(authService.deleteAccountById(userId));
    }

    @GetMapping("/internal/users/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable Integer userId) {
        log.info("API called: /auth/internal/users/{}/validate", userId);
        boolean isValid = authService.isUserValid(userId);
        if (isValid) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @GetMapping("/internal/users/batch")
    public ResponseEntity<Map<Integer, UserInfoResponse>> getUsersBatch(@RequestParam List<Integer> ids) {
        log.info("API called: /auth/internal/users/batch with {} ids", ids.size());
        List<com.devapix.auth_service.model.User> users = authService.findByIds(ids);
        Map<Integer,UserInfoResponse> response = users.stream().collect(Collectors.toMap(User::getUserid, u ->new UserInfoResponse(u.getUserid(), u.getName(), u.getEmail())));
        return ResponseEntity.ok(response);
    }
}