package com.example.convertform.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public interface IFileReadService {
    public Object[] readInputFile (InputStream fileInputStream) throws IOException;
}
