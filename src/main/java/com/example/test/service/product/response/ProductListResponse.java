package com.example.test.service.product.response;

import java.math.BigDecimal;

public record ProductListResponse (Long id, String name, String description, BigDecimal price, String categoryName){}
