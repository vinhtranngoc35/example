package com.example.test.service.product;

import com.example.test.domain.Product;
import com.example.test.service.product.request.ProductSaveRequest;
import com.example.test.service.product.response.ProductDetailResponse;
import com.example.test.service.product.response.ProductListResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {

    Page<ProductListResponse> findAllWithSearch(String search, Long categoryId, Pageable pageable);
    void create(ProductSaveRequest request);
    void update(ProductSaveRequest request, long id) throws JsonMappingException;

    void delete(long id);

    Product findById(long id);

    ProductDetailResponse findDetailById(long id);

    long count();
    void init();
}
