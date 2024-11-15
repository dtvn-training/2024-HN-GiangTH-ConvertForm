package com.example.convertform.service;

import jakarta.validation.ValidationException;

import java.util.List;

public interface IFileValidateService {
    public String validateSingleFieldData(Object[] object) throws ValidationException;
}
