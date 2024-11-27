package com.example.convertform.service;

import com.example.convertform.dto.output.*;

import java.util.List;

public interface IFileConvertService {
    ConversionResult convertData(Object[] objects);
}
