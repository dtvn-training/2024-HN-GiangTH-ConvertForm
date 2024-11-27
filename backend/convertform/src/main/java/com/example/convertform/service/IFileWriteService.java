package com.example.convertform.service;

import com.example.convertform.dto.output.ConversionResult;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IFileWriteService {
    public byte[] writeExcelFileDemo(ConversionResult conversionResult) throws IOException;
    public byte[] writeExcelFile(ConversionResult conversionResult);
    public void writeErrorFile(List<ValidationErrorResponseDTO> validationErrorResponseDTOList) throws IOException;
}
