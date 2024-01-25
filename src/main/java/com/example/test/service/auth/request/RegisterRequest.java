package com.example.test.service.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "Username cannot be blank") String username, String password) {}