package com.example.test.service.category;


import com.example.test.domain.Category;
import com.example.test.domain.Product;
import com.example.test.repository.ICategoryRepository;
import com.example.test.repository.IProductRepository;
import com.example.test.service.category.request.CategorySaveRequest;
import com.example.test.service.product.IProductService;
import com.example.test.service.product.request.ProductSaveRequest;
import com.example.test.service.product.response.ProductListResponse;
import com.example.test.service.request.SelectOptionRequest;
import com.example.test.service.response.SelectOptionResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SelectOptionResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new SelectOptionResponse(category.getId(), category.getName())).toList();
    }

    @Override
    public void create(CategorySaveRequest request) {
        Category category = new Category();
        category.setName(request.name());
        categoryRepository.save(category);
    }

    @Override
    public void update(CategorySaveRequest request, long id){
        Category category = findById(id);
        category.setName(request.name());
        categoryRepository.save(category);
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void init() {
        Category category = new Category();
        category.setName("Category 1");
        categoryRepository.save(category);
    }
}