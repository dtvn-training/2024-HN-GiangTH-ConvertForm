package com.example.convertform.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileProcessService {
    public ResponseEntity<?> processExcelFile(MultipartFile inputFile) throws IOException;
}
