package com.example.convertform.validation.common;

import com.example.convertform.dto.input.BaseRecord;
import com.example.convertform.dto.input.PlacementTable;
import com.example.convertform.dto.input.RecordColIndex;
import com.example.convertform.dto.input.SearchTargetTable;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromTargetTableDTO;
import jakarta.validation.*;

import java.lang.reflect.Field;
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
        List<ErrorFromTargetTableDTO> errorFromTargetTableDTOS = new ArrayList<>();
        int errorNo = -1;

        String sheetName = o.getClass().getSimpleName();

        for (ConstraintViolation<Object> violation : violations) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            int endIndex = propertyPath.indexOf(']');

            //get object which have violation to get "No." value
            Object errorObject = violation.getLeafBean();
            System.out.println(message + errorObject);

            //Object from Sheet 1 -> 4, have "No." number correspond to each record
            //Else is rest Sheet, like Targets table, which presented in another format
            if (errorObject instanceof BaseRecord) {
                errorNo = ((BaseRecord) errorObject).getNo();

                if ((propertyPath.length() - 1) != endIndex) {
                    String fieldName = propertyPath.substring(endIndex + 2);

                    try {
                        Field field = errorObject.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        String colIndex = field.getAnnotation(RecordColIndex.class).colName();
                        errorRecordDTOList.add(new ErrorFromRecordDTO(errorNo, fieldName, message, colIndex));
                    } catch (NoSuchFieldException ex) {
                        errorRecordDTOList.add(new ErrorFromRecordDTO(errorNo, fieldName, message));
                        throw new RuntimeException(errorObject.toString() + ex);
                    }
                } else {
                    errorRecordDTOList.add(new ErrorFromRecordDTO(errorNo, "Start Date, End Date", message));
                }
            } else {
                if (errorObject instanceof PlacementTable placementTable) {
                    errorFromTargetTableDTOS.add(new ErrorFromTargetTableDTO(message, placementTable.getTargetName()));
                } else if (errorObject instanceof SearchTargetTable searchTable) {
                    errorFromTargetTableDTOS.add(new ErrorFromTargetTableDTO(message, searchTable.getTargetName()));
                }
            }
        }
        return new ValidationErrorResponseDTO(sheetName, errorRecordDTOList, errorFromTargetTableDTOS);
    }
}
