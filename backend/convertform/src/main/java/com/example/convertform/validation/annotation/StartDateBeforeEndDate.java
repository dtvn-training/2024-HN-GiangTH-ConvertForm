package com.example.convertform.validation.annotation;

import com.example.convertform.validation.StartBeforeEndDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType. METHOD,ElementType. FIELD,ElementType. ANNOTATION_TYPE,ElementType. CONSTRUCTOR,ElementType. PARAMETER,ElementType. TYPE_USE})
@Constraint(validatedBy = StartBeforeEndDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartDateBeforeEndDate {
    String message() default "Start date must be before end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
