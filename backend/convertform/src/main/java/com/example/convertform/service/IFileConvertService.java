package com.example.convertform.service;

import com.example.convertform.dto.output.*;

import java.util.List;

public interface IFileConvertService<T, R> {
    public R itemConvert(T source);
    List<R> listConvert(List<T> sources);
}
