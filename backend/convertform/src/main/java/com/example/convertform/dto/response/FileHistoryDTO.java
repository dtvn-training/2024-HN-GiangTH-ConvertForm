package com.example.convertform.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileHistoryDTO {
    private FileInfoDTO originalFile;
    private List<FileInfoDTO> relatedFiles;
}
