package com.example.convertform.controller;

import com.example.convertform.service.impl.FileProcessService;
import com.example.convertform.service.impl.FileReadService;
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

    @PostMapping("/upload")
    ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileProcessService.processExcelFile(file);
    }

    @GetMapping("/get")
    String get() {
        return "Still work";
    }
}
