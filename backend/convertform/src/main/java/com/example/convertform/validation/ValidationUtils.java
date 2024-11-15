package com.example.convertform.validation;

import com.example.convertform.dto.input.BaseRecord;
import com.example.convertform.dto.input.CampaignRecord;
import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ValidationErrorRecordDTO;
import com.example.convertform.dto.response.ValidationErrorSheetDTO;
import jakarta.validation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationUtils {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T>Set<ConstraintViolation<T>> validate(T object) {
        return validator.validate(object);
    }

    /**
     * This function take set of Validation constraint violations (error) then process data.
     * <br />
     * Make response easier to read
     * @param violations Constraint violations List
     * @return List of DTO objects represent for validation errors (use for response purpose)
     */
    public static ValidationErrorResponseDTO buildResponseDTO(Object o, Set<ConstraintViolation<Object>> violations) {
        List<ValidationErrorRecordDTO> errorRecordDTOList = new ArrayList<>();
        List<ValidationErrorSheetDTO> errorSheetDTOList = new ArrayList<>();
        int errorNo = 300;

        String sheetName = o.getClass().getSimpleName();

        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            int endIndex = propertyPath.indexOf(']');

            //get object which have violation to get "No." value
            Object errorObject = violation.getLeafBean();

            if (errorObject instanceof BaseRecord) {
                errorNo = ((BaseRecord) errorObject).getNo();
            } else {
                // If error come from other field, not from record in Sheet
                // Add to ErrorSheet
                errorSheetDTOList.add(new ValidationErrorSheetDTO(message, propertyPath));
                // Skip get the fieldName
                continue;
            }

            // Skip path until "]." , the rest string is field name
            // If "]" char is the end of path, that's mean its Class-level error, not field error
            if ((propertyPath.length() - 1) == endIndex) {
                errorRecordDTOList.add(new ValidationErrorRecordDTO(0, "Class-level error", message));
            }
            else {
                String fieldName = propertyPath.substring(endIndex + 2);
                errorRecordDTOList.add(new ValidationErrorRecordDTO(errorNo, fieldName, message));
            }
        }

        return new ValidationErrorResponseDTO(sheetName, errorRecordDTOList, errorSheetDTOList);
    }
}
