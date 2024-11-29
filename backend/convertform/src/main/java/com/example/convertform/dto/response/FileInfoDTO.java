package com.example.convertform.dto.response;

import com.example.convertform.entity.FileType;

import java.time.LocalDateTime;

public class FileInfoDTO {
    private Integer fileId;
    private String fileName;
    private FileType type;
    private LocalDateTime createAt;
}
