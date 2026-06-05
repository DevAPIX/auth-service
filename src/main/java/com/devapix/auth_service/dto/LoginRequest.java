package com.devapix.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "{email.required}")
    private String email;

    @NotBlank(message = "{password.required}")
    private String password;

}