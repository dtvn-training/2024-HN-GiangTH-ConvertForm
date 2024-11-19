package com.example.convertform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorFromRecordDTO {
    private int recordNo;
    private String fieldName;
    private String message;
}
