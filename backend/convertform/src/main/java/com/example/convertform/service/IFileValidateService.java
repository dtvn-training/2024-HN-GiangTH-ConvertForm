package com.example.convertform.service;

import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import jakarta.validation.ValidationException;

import java.util.List;

public interface IFileValidateService {
    public List<ValidationErrorResponseDTO> validateSingleFieldData(Object[] object) throws ValidationException;
    public void validateRelated(Object[] objects, List<ValidationErrorResponseDTO> validationErrorResponseDTOList) throws ValidationException;
}
