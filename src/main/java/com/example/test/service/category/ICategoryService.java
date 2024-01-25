package com.example.test.service.category;

import com.example.test.domain.Category;
import com.example.test.domain.Product;
import com.example.test.service.category.request.CategorySaveRequest;
import com.example.test.service.product.request.ProductSaveRequest;
import com.example.test.service.product.response.ProductListResponse;
import com.example.test.service.response.SelectOptionResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {

    List<SelectOptionResponse> findAll();
    void create(CategorySaveRequest request);
    void update(CategorySaveRequest request, long id);

    void delete(long id);

    Category findById(long id);
    long count();

    void init();
}
