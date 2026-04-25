package com.devapix.auth_service.service;

import com.devapix.auth_service.dto.LoginRequest;
import com.devapix.auth_service.dto.RegisterRequest;
import com.devapix.auth_service.dto.response.DeleteResponse;
import com.devapix.auth_service.dto.response.LoginResponse;
import com.devapix.auth_service.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    DeleteResponse deleteAccount(String email);

}