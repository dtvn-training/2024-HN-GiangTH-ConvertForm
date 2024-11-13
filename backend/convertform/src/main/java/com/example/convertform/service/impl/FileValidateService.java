package com.example.convertform.service.impl;

import com.example.convertform.dto.input_sheet.CampaignSheet;
import com.example.convertform.service.IFileValidateService;
import com.example.convertform.validation.ValidationUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FileValidateService implements IFileValidateService {
    @Override
    public String validateData(Object object) throws ValidationException{
        Set<ConstraintViolation<Object>> violations = ValidationUtils.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation errors:\n");
            sb.append(object.toString());
            sb.append("\n ");
            for (ConstraintViolation<Object> violation : violations) {
                sb.append("- ").append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
            }
//            throw new ValidationException(sb.toString());
            return sb.toString();
        }
        return "No problem";
    }
}
