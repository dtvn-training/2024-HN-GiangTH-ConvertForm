package com.example.convertform.controller;

import com.example.convertform.service.impl.FileReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileReadService fileReadService;

    @PostMapping("/upload")
    ResponseEntity<String> upload() throws IOException {
        String ans = fileReadService.readInputFileDemo();
        return ResponseEntity.status(500).body(ans);
    }

    @GetMapping("/get")
    String get() {
        return "Still work";
    }
}
