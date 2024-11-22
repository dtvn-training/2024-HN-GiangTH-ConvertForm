package com.example.convertform.validation.annotation;

import com.example.convertform.validation.RadiusLimitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RadiusLimitValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RadiusLimit {
    String message() default "Limit error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
