package com.example.convertform.validation;

import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.validation.annotation.KeywordListNameRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class KeywordListNameValidator implements ConstraintValidator<KeywordListNameRequired, SearchTargetTable> {
    @Override
    public boolean isValid(SearchTargetTable searchTargetTable, ConstraintValidatorContext constraintValidatorContext) {
        boolean keywordFilled = searchTargetTable.getSearchKeywordRecords().getFirst().getKeyword() != null;
        boolean targetNameFilled = (searchTargetTable.getTargetName() != null && !Objects.equals(searchTargetTable.getTargetName(), "Choose a targeting name"));

        if (keywordFilled && targetNameFilled) {
            return searchTargetTable.getListName() != null;
        }
        return true;
    }
}
