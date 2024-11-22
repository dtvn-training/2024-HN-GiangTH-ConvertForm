package com.example.convertform.service.impl;

import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromTargetTableDTO;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileWriteService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileWriteService implements IFileWriteService {
    @Override
    public byte[] writeExcelFile() {
        return new byte[0];
    }

    @Override
    public void writeErrorFile(List<ValidationErrorResponseDTO> validationErrorResponseDTOList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        FileOutputStream outFile = new FileOutputStream("C:\\Users\\GiangTH\\Downloads\\out_new.xlsx", false);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (ValidationErrorResponseDTO err : validationErrorResponseDTOList) {
            if (err.getErrorSheetDTOList().isEmpty() && err.getErrorRecordDTOList().isEmpty()) continue;
            Sheet sheet = workbook.createSheet(err.getSheetName());

            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            String[] headers = {"Error Table", "Record No.", "Column name", "Error Description"};

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            if (err.getErrorSheetDTOList() != null && !err.getErrorSheetDTOList().isEmpty()) {
                for (ErrorFromTargetTableDTO sheetError : err.getErrorSheetDTOList()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(sheetError.getTargetName());
                    row.createCell(1).setBlank();
                    row.createCell(2).setBlank();
                    row.createCell(3).setCellValue(sheetError.getMessage());
                }
            }

            if (err.getErrorRecordDTOList() != null && !err.getErrorRecordDTOList().isEmpty()) {
                for (ErrorFromRecordDTO recordError : err.getErrorRecordDTOList()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setBlank();
                    row.createCell(1).setCellValue(recordError.getRecordNo());
                    row.createCell(2).setCellValue(recordError.getFieldName());
                    row.createCell(3).setCellValue(recordError.getMessage());
                }
            }
        }
        workbook.write(outFile);
        System.out.println("Error File write successfully");
    }
}
