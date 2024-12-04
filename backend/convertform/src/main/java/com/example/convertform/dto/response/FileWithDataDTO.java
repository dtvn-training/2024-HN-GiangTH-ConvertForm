package com.example.convertform.dto.response;

import lombok.Data;

@Data
public class FileWithDataDTO {
        private String fileName;
        private byte[] data;
}
