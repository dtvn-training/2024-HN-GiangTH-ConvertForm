package com.example.convertform.validation;

import com.example.convertform.validation.annotation.RadiusLimit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadiusLimitValidator implements ConstraintValidator<RadiusLimit, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return true;
        int radius = Integer.parseInt(s.substring(0, s.length() - 2));
        return radius >= 1 && radius <= 80;
    }
}
