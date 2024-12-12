package com.example.convertform.service.impl;

import com.example.convertform.dto.output.CampaignOutput;
import com.example.convertform.dto.output.ConversionResult;
import com.example.convertform.dto.output.OutputExcelColumn;
import com.example.convertform.dto.response.ErrorFromRecordDTO;
import com.example.convertform.dto.response.ErrorFromTargetTableDTO;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileWriteService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FileWriteService implements IFileWriteService {
    @Override
    public byte[] writeExcelFile(ConversionResult conversionResult) {
        return new byte[]{};
    }

    @Override
    public byte[] writeExcelFileDemo(ConversionResult result) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        writeSheet(workbook, "CP", result.getCampaignOutputs());
        writeSheet(workbook, "AdGp", result.getAdGroupOutputs());
        writeSheet(workbook, "AD_txt", result.getAdOutputs());
        writeSheet(workbook, "Gender", result.getGenderOutputs());
        writeSheet(workbook, "Age", result.getAgeOutputs());
        writeSheet(workbook, "Region", result.getRegionOutputs());
        writeSheet(workbook, "Device", result.getDeviceOutputs());
        writeSheet(workbook, "Search Targeting", result.getSearchTargetOutputs());
        writeSheet(workbook, "Site Category", result.getSiteCategoryOutputs());
        writeSheet(workbook, "Placement", result.getPlacementOutputs());

        workbook.write(bos);

        System.out.println("Write output file (CP) successfully");
        workbook.close();

        return bos.toByteArray();
    }

    @Override
    public byte[] writeSharedLibFile(ConversionResult conversionResult) throws IOException {
        if (conversionResult.getKeywordListOutputs().isEmpty() && conversionResult.getPlacementListOutputs().isEmpty()) return null;

        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        writeSheet(workbook, "Search Keyword List", conversionResult.getKeywordListOutputs());
        writeSheet(workbook, "Placement List", conversionResult.getPlacementListOutputs());

        workbook.write(bos);
        System.out.println("Write shared lib successfully");
        workbook.close();

        return bos.toByteArray();
    }

    @Override
    public byte[] writeErrorFile(List<ValidationErrorResponseDTO> validationErrorResponseDTOList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

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
        workbook.write(bos);
        System.out.println("Error File write successfully");
        workbook.close();

        return bos.toByteArray();
    }

    private <T> void writeSheet(Workbook workbook, String sheetName, List<T> list) {
        if (list.isEmpty()) return;
        int rowCount = 0;
        Sheet sheet = workbook.createSheet(sheetName);

        Row headerRow = sheet.createRow(rowCount++);
        CellStyle headerStyle = createHeaderStyle(workbook);

        Field[] fieldList = list.getFirst().getClass().getDeclaredFields();
        System.out.println("Field of : "+ sheetName + "   " +  Arrays.toString(fieldList));

        //Set Column 0 header "Line Number"
        Cell lineNumber = headerRow.createCell(0);
        lineNumber.setCellValue("Line Number");
        lineNumber.setCellStyle(headerStyle);

        //Set others header depends on field
        for (Field field : fieldList) {
            String colName = field.getAnnotation(OutputExcelColumn.class).colName();
            int order = field.getAnnotation(OutputExcelColumn.class).order();

            Cell headerCell = headerRow.createCell(order);
            headerCell.setCellStyle(headerStyle);
            headerCell.setCellValue(colName);
        }

        //Fill data into sheet from converted list
        for (T item : list) {
            Row dataRow = sheet.createRow(rowCount);
            dataRow.createCell(0).setCellValue(rowCount);

            for (Field field : fieldList) {
                field.setAccessible(true);
                Cell dataCell = dataRow.createCell(field.getAnnotation(OutputExcelColumn.class).order());

                try {
                    Object value = field.get(item);
                    setCellValue(dataCell, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            rowCount++;
        }

        sheet.createFreezePane(0, 1);
        for (int i = 0; i <= fieldList.length; i++) {
            sheet.autoSizeColumn(i);
            int currentWidth = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, currentWidth + 500);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private void setCellValue(Cell cell, Object value) {
        CellStyle dateStyle = cell.getSheet().getWorkbook().createCellStyle();
        CreationHelper creationHelper = cell.getSheet().getWorkbook().getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy"));
        switch (value) {
            case null -> cell.setCellValue("");
            case Number number -> cell.setCellValue(number.doubleValue() + "$");
            case Boolean b -> cell.setCellValue(b);
            case Date date -> {
                cell.setCellValue(date);
                cell.setCellStyle(dateStyle);
            }
            default -> cell.setCellValue(value.toString());
        }
    }
}
