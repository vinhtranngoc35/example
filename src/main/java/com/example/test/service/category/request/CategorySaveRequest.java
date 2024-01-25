package com.example.test.service.category.request;

import jakarta.validation.constraints.NotBlank;

public record CategorySaveRequest(@NotBlank(message = "Name cannot be blank") String name) {
}
