package com.example.test.service.product;


import com.example.test.domain.Category;
import com.example.test.domain.Product;
import com.example.test.repository.IProductRepository;
import com.example.test.service.IExistsService;
import com.example.test.service.product.request.ProductSaveRequest;
import com.example.test.service.product.response.ProductDetailResponse;
import com.example.test.service.product.response.ProductListResponse;
import com.example.test.util.AppConstant;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService implements IProductService, IExistsService {

    private final IProductRepository productRepository;

    private final ObjectMapper objectMapper;

    private final String ENTITY = "Product";

    public ProductService(IProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Page<ProductListResponse> findAllWithSearch(String search,Long categoryId, Pageable pageable) {
        search = "%" + search + "%";
        return productRepository.findAllWithSearch(search, categoryId, pageable);
    }

    @Override
    public void create(ProductSaveRequest request) {
        Product product = objectMapper.convertValue(request, Product.class);
        productRepository.save(product);
    }

    @Override
    public void update(ProductSaveRequest request, long id) throws JsonMappingException {
        Product product = findById(id);
        objectMapper.updateValue(product, request);
        productRepository.save(product);
    }

    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() ->new RuntimeException(String.format(AppConstant.ID_NOT_FOUND, id, ENTITY)));
    }

    @Override
    public ProductDetailResponse findDetailById(long id) {
        Product product = findById(id);
        return new ProductDetailResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().toString(),
                product.getCategory().getId());

    }

    @Override
    public long count() {
        return productRepository.count();
    }

    @Override
    public void init() {
        //fake data products
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(BigDecimal.TEN);
        product.setDescription("Product 1 description");
        product.setCategory(new Category(1L));
        products.add(product);
        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.TEN);
        product2.setDescription("Product 2 description");
        product2.setCategory(new Category(1L));
        products.add(product2);
        productRepository.saveAll(products);

    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public boolean isService(String className) {
        return Objects.equals(className, Product.class.getName());
    }
}