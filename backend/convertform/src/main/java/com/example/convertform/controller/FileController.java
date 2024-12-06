package com.example.convertform.controller;

import com.example.convertform.service.impl.FileProcessService;
import com.example.convertform.service.impl.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<?> downloadFileById(@RequestParam("fileName") String fileName, @PathVariable Integer fileId) {
        return fileStorageService.downloadFileById(fileId, fileName);
    }

    @GetMapping("/history/{uid}")
    ResponseEntity<?> getUserHistory(@PathVariable Integer uid) {
        return fileStorageService.getUserHistory(uid == null ? 1 : uid);
    }

    @DeleteMapping("/delete/{fileId}")
    ResponseEntity<?> deleteFileHistory(@PathVariable Integer fileId) {
        return fileStorageService.deleteFile(fileId);
    }
}
