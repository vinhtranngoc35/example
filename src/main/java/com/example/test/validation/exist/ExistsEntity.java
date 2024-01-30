package com.example.test.validation.exist;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistEntityValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsEntity {
    String message() default "Id not found";
    Class<?> value() default Object.class;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}