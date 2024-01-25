package com.example.test.service.product.request;

import com.example.test.service.request.SelectOptionRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductSaveRequest(
    @NotBlank(message = "Name cannot be blank") String name,
    String description,
    @Min(value = 1_000, message = "Price must be greater than 1,000")BigDecimal price,
    SelectOptionRequest category) { }
