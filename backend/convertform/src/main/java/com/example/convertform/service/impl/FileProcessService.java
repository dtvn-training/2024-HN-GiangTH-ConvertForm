package com.example.convertform.service.impl;

import com.example.convertform.service.IFileProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class FileProcessService implements IFileProcessService {
    @Autowired
    private final FileReadService fileReadService;
    @Override
    public byte[] processExcelFile() {
        return new byte[0];
    }
}
