package com.devapix.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

        @NotBlank(message = "{name.required}")
        @Size(min = 3, max = 50, message = "{name.size}")
        private String name;

        @NotBlank(message = "{email.required}")
        @Email(message = "{email.invalid}")
        private String email;

        @NotBlank(message = "{password.required}")
        @Size(min = 6, message = "{password.size}")
        private String password;
}