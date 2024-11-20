package com.example.convertform.service;

import com.example.convertform.dto.response.ValidationErrorResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IFileWriteService {
    public byte[] writeExcelFile();
    public void writeErrorFile(List<ValidationErrorResponseDTO> validationErrorResponseDTOList) throws IOException;
}
