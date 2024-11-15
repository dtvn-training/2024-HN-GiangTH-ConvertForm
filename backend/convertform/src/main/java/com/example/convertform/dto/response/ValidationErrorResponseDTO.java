package com.example.convertform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponseDTO {
    private String sheetName;
    private List<ValidationErrorRecordDTO> errorRecordDTOList;
    private List<ValidationErrorSheetDTO> errorSheetDTOList;
}
