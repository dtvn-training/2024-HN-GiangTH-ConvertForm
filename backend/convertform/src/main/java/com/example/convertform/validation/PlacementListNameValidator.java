package com.example.convertform.validation;

import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.validation.annotation.PlacementListNameRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PlacementListNameValidator implements ConstraintValidator<PlacementListNameRequired, PlacementTable> {
    @Override
    public boolean isValid(PlacementTable placementTable, ConstraintValidatorContext constraintValidatorContext) {
        boolean includeValid = !Objects.equals(placementTable.getInclude(), "▼Select");
        boolean domainValid = placementTable.getPlacementRecords().getFirst().getDomain() != null;

        if (includeValid || domainValid) {
            return placementTable.getListName() != null;
        }
        return true;
    }
}
