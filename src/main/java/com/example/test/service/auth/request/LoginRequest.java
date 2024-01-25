package com.example.test.service.auth.request;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest(@NotBlank(message = "Username cannot be blank") String username,
                           @NotBlank(message = "Password cannot be blank") String password) {
}