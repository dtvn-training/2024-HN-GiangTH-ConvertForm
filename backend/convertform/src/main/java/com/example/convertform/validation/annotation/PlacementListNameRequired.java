package com.example.convertform.validation.annotation;

import com.example.convertform.validation.PlacementListNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType. METHOD,ElementType. FIELD,ElementType. ANNOTATION_TYPE,ElementType. CONSTRUCTOR,ElementType. PARAMETER,ElementType. TYPE_USE})
@Constraint(validatedBy = PlacementListNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacementListNameRequired {
    String message() default "Required placement list name when domain/include/exclude entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
