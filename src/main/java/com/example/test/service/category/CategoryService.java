package com.example.test.service.category;


import com.example.test.domain.Category;
import com.example.test.repository.ICategoryRepository;
import com.example.test.repository.IProductRepository;
import com.example.test.service.IExistsService;
import com.example.test.service.category.request.CategorySaveRequest;
import com.example.test.service.response.SelectOptionResponse;
import com.example.test.util.AppConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService implements ICategoryService, IExistsService {

    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;

    private final String ENTITY = "Category";

    public CategoryService(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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
    @Transactional
    public void delete(long id) {
        productRepository.deleteAllByCategory_Id(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, id, ENTITY)));
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

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean isService(String className) {
        return Objects.equals(className, Category.class.getName());
    }
}