package com.example.convertform.validation;

import com.example.convertform.validation.annotation.SelectionRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class AllowedValueValidator implements ConstraintValidator<SelectionRequired, String> {
    private List<String> allowedValues;

    @Override
    public void initialize(SelectionRequired constraintAnnotation) {
        allowedValues = Arrays.asList(constraintAnnotation.values());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;

        return allowedValues.contains(s);
    }
}
