package com.example.convertform.entity;

import com.example.convertform.entity.enums.FileType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExcelFile {
    private Integer id;
    private Integer uid;
    private String fileName;
    private FileType type;
    private byte[] data;
    private LocalDateTime time;
    private Integer orgFileId;
}
