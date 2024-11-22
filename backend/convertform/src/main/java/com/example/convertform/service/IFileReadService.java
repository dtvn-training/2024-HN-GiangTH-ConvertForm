package com.example.convertform.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public interface IFileReadService {
    public String readInputFileDemo () throws IOException;
    public Object[] readInputFile (FileInputStream fileInputStream) throws IOException;
}
