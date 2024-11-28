package com.example.convertform.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExcelFile {
    private Integer id;
    private Integer userId;
    private String fileName;
    private FileType type;
    private byte[] data;
    private LocalDateTime time;
    private Integer orgFileId;
}
