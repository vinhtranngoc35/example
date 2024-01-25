package com.example.test.repository;

import com.example.test.domain.Product;
import com.example.test.service.product.response.ProductListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.example.test.service.product.response.ProductListResponse(p.id, p.name, p.description, p.price, c.name) " +
            "FROM Product p JOIN p.category c " +
            "WHERE (:categoryId is null or c.id = :categoryId) AND " +
            "(p.name LIKE :search or p.description LIKE :search or c.name LIKE :search)")
    Page<ProductListResponse> findAllWithSearch(String search, Long categoryId, Pageable pageable);

    void deleteAllByCategory_Id(Long categoryId);
}