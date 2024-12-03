package com.example.convertform.dto.response;

import com.example.convertform.entity.FileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileInfoDTO {
    private Integer fileId;
    private String fileName;
    private FileType type;
    private LocalDateTime createAt;
}
