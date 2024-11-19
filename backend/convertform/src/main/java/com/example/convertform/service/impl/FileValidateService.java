package com.example.convertform.service.impl;

import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromSheetDTO;
import com.example.convertform.service.IFileValidateService;
import com.example.convertform.validation.ValidationUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
                validationErrorResponseDTO.getErrorRecordDTOList().sort(Comparator.comparing(ErrorFromRecordDTO::getRecordNo));
                sb.append(validationErrorResponseDTO.getSheetName());
                sb.append("\n");
                for (ErrorFromSheetDTO errorFromSheetDTO : validationErrorResponseDTO.getErrorSheetDTOList()) {
                    sb.append("Cell: ").append(errorFromSheetDTO.getCellName()).append(" - ").append(errorFromSheetDTO.getMessage()).append("\n");
                }
                for (ErrorFromRecordDTO errorFromRecordDTO : validationErrorResponseDTO.getErrorRecordDTOList()) {
                    sb.append("No: ").append(errorFromRecordDTO.getRecordNo()).append(" - Column name: ").append(errorFromRecordDTO.getFieldName())
                            .append(" - ").append(errorFromRecordDTO.getMessage()).append("\n");
                }
            }
        }
        return sb.toString();
    }
}
