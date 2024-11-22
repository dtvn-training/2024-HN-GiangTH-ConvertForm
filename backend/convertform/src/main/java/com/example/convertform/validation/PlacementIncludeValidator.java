package com.example.convertform.validation;

import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.validation.annotation.PlacementIncludeRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PlacementIncludeValidator implements ConstraintValidator<PlacementIncludeRequired, PlacementTable> {
    @Override
    public boolean isValid(PlacementTable placementTable, ConstraintValidatorContext constraintValidatorContext) {
        boolean listNameValid = placementTable.getListName() != null;
        boolean domainValid = placementTable.getPlacementRecords().getFirst().getDomain() != null;

        if (listNameValid || domainValid) {
            return (placementTable.getInclude() != null && !placementTable.getInclude().equals("▼Select"));
        }

        return true;
    }
}
