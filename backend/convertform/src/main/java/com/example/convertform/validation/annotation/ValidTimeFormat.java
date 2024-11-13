package com.example.convertform.validation.annotation;

import com.example.convertform.validation.TimeFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeFormat {
    String message() default "Invalid time format. Expected HH:mm";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
