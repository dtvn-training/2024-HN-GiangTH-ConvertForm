package com.example.convertform.validation.annotation;

import com.example.convertform.validation.KeywordListNameValidator;
import com.example.convertform.validation.PlacementListNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType. METHOD,ElementType. FIELD,ElementType. ANNOTATION_TYPE,ElementType. CONSTRUCTOR,ElementType. PARAMETER,ElementType. TYPE_USE})
@Constraint(validatedBy = KeywordListNameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeywordListNameRequired {
    String message() default "Required Search keyword list name when keyword/target name entered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
