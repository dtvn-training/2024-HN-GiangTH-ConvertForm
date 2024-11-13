package com.example.convertform.service;

import jakarta.validation.ValidationException;

public interface IFileValidateService {
    public String validateData(Object object) throws ValidationException;
}
