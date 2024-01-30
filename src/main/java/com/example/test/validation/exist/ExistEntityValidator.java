package com.example.test.validation.exist;

import com.example.test.service.IExistsService;
import com.example.test.service.request.SelectOptionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExistEntityValidator implements ConstraintValidator<ExistsEntity, Object> {

    private String clazzName;
    private final List<IExistsService> existsServices;

    private final Map<String, IExistsService> existsServiceMap = new HashMap<>();

    @Override
    public void initialize(ExistsEntity constraintAnnotation) {
        clazzName = constraintAnnotation.value().getName();
    }

    public ExistEntityValidator(List<IExistsService> existsServices) {
        this.existsServices = existsServices;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        long id;
        if (value instanceof SelectOptionRequest) {
            id = ((SelectOptionRequest) value).id();
        }else {
            id = (Objects.isNull(value) ? 0 : (Long) value);
        }

        if (existsServiceMap.containsKey(clazzName)) {
            return existsServiceMap.get(clazzName).existsById(id);
        }

        for (IExistsService existsService : existsServices) {

            if (existsService.isService(clazzName)) {
                existsServiceMap.put(clazzName, existsService);
                return existsService.existsById(id);
            }
        }
        return false;
    }
}