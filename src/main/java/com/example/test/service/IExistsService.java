package com.example.test.service;

public interface IExistsService {

    boolean existsById(Long id);

    boolean isService(String className);
}