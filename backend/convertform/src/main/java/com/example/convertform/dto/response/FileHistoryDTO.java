package com.example.convertform.dto.response;

import com.example.convertform.entity.FileType;

import java.time.LocalDateTime;

public class FileHistoryDTO {
    private Integer id;
    private String fileName;
    private FileType type;
    private LocalDateTime time;
    private Integer orgFileId;
}
