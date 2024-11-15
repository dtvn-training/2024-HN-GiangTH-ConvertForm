package com.example.convertform.service.impl;

import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ValidationErrorRecordDTO;
import com.example.convertform.dto.response.ValidationErrorSheetDTO;
import com.example.convertform.service.IFileValidateService;
import com.example.convertform.validation.ListValidator;
import com.example.convertform.validation.ValidationUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class FileValidateService implements IFileValidateService {
    @Override
    public String validateSingleFieldData(Object[] objects) throws ValidationException{
        StringBuilder sb = new StringBuilder();

        for (Object o : objects) {
            Set<ConstraintViolation<Object>> violations = ValidationUtils.validate(o);

            if (!violations.isEmpty()) {
                sb.append("Validation error at: ");

                ValidationErrorResponseDTO validationErrorResponseDTO = ValidationUtils.buildResponseDTO(o, violations);
                validationErrorResponseDTO.getErrorRecordDTOList().sort(Comparator.comparing(ValidationErrorRecordDTO::getRecordNo));
                sb.append(validationErrorResponseDTO.getSheetName());
                sb.append("\n");
                for (ValidationErrorSheetDTO validationErrorSheetDTO : validationErrorResponseDTO.getErrorSheetDTOList()) {
                    sb.append("Cell: ").append(validationErrorSheetDTO.getCellName()).append(" - ").append(validationErrorSheetDTO.getMessage()).append("\n");
                }
                for (ValidationErrorRecordDTO validationErrorRecordDTO : validationErrorResponseDTO.getErrorRecordDTOList()) {
                    sb.append("No: ").append(validationErrorRecordDTO.getRecordNo()).append(" - Column name: ").append(validationErrorRecordDTO.getFieldName())
                            .append(" - ").append(validationErrorRecordDTO.getMessage()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
