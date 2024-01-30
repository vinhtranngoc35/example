package com.example.test.controller.rest;

import com.example.test.domain.Product;
import com.example.test.service.product.IProductService;
import com.example.test.service.product.request.ProductSaveRequest;
import com.example.test.service.product.response.ProductDetailResponse;
import com.example.test.service.product.response.ProductListResponse;
import com.example.test.validation.exist.ExistsEntity;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final IProductService productService;

    public ProductRestController(IProductService productService) {
        this.productService = productService;
    }

    //allow anonymous user

    @GetMapping
    public ResponseEntity<Page<ProductListResponse>> findAllWithSearch(@RequestParam(required = false, defaultValue = "") String search
                                                                       , @RequestParam(required = false) Long categoryId
                                                                        , @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.findAllWithSearch(search, categoryId, pageable));
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody ProductSaveRequest request) {
        productService.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ProductSaveRequest request,
                                        @ExistsEntity(value = Product.class, message = "Product not found" ) @PathVariable Long id) throws JsonMappingException {
        productService.update(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> findById(@ExistsEntity(value = Product.class, message = "Product not found") @PathVariable Long id) {
        return ResponseEntity.ok(productService.findDetailById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}