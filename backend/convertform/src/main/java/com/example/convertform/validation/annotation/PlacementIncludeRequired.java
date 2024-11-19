package com.example.convertform.validation.annotation;

import com.example.convertform.validation.PlacementIncludeValidator;
import com.example.convertform.validation.PlacementListNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType. METHOD,ElementType. FIELD,ElementType. ANNOTATION_TYPE,ElementType. CONSTRUCTOR,ElementType. PARAMETER,ElementType. TYPE_USE})
@Constraint(validatedBy = PlacementIncludeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlacementIncludeRequired {
    String message() default "Required Include/Exclude when domain/include/exclude entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
