package com.example.test.controller.rest;

import com.example.test.domain.Category;
import com.example.test.service.category.ICategoryService;
import com.example.test.service.category.request.CategorySaveRequest;
import com.example.test.service.response.SelectOptionResponse;
import com.example.test.validation.exist.ExistsEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final ICategoryService categoryService;

    public CategoryRestController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    public ResponseEntity<List<SelectOptionResponse>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CategorySaveRequest request){
        categoryService.create(request);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategorySaveRequest request,
                                       @ExistsEntity(value = Category.class, message = "Category not found") @PathVariable Long id){
        categoryService.update(request, id);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}