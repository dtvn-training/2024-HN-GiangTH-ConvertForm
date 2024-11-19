package com.example.convertform.validation;

import com.example.convertform.dto.input.BaseRecord;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromSheetDTO;
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
        List<ErrorFromRecordDTO> errorRecordDTOList = new ArrayList<>();
        List<ErrorFromSheetDTO> errorSheetDTOList = new ArrayList<>();
        int errorNo = -1;

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
                // Other error come from other source, not from record-level
                // Add to ErrorSheet
                errorSheetDTOList.add(new ErrorFromSheetDTO(message, propertyPath));
                // Skip get the fieldName
                continue;
            }

            // path have form : object[index].fieldName
            // Skip path until "]." , the rest string is field name
            // If "]" char is the end of path, that's mean its Class-level error, not field error
            if ((propertyPath.length() - 1) != endIndex) {
                String fieldName = propertyPath.substring(endIndex + 2);
                errorRecordDTOList.add(new ErrorFromRecordDTO(errorNo, fieldName, message));
            }
        }
        return new ValidationErrorResponseDTO(sheetName, errorRecordDTOList, errorSheetDTOList);
    }
}
