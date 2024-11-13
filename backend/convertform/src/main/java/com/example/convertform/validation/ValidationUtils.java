package com.example.convertform.validation;

import jakarta.validation.*;

import java.util.Set;

public class ValidationUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T>Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }
}
