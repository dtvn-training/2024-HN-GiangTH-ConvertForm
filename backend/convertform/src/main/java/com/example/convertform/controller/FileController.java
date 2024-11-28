package com.example.convertform.controller;

import com.example.convertform.service.impl.FileProcessService;
import com.example.convertform.service.impl.FileReadService;
import com.example.convertform.service.impl.repository.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class FileController {
    @Autowired
    FileProcessService fileProcessService;
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/upload")
    ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileProcessService.processExcelFile(file);
    }

    @GetMapping("/download/{fileId}")
    ResponseEntity<byte[]> downloadFileById(@RequestParam("fileName") String fileName, @PathVariable Integer fileId) {
        return fileStorageService.downloadFileById(fileId, fileName);
    }

    @GetMapping("/get")
    String get() {
        return "Still work";
    }
}
