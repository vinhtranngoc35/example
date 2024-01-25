package com.example.test.config.init;

import com.example.test.service.category.ICategoryService;
import com.example.test.service.product.IProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// the class for init data in database before start the application for the first time
@Component
public class InitData implements ApplicationRunner {
    private final IProductService productService;

    private final ICategoryService categoryService;

    public InitData(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(categoryService.count() == 0) {
            categoryService.init();
        }
        if(productService.count() == 0) {
            productService.init();
        }

    }
}
