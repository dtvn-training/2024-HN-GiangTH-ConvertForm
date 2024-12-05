package com.example.convertform.service.impl;

import com.example.convertform.dto.output.ConversionResult;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.entity.ExcelFile;
import com.example.convertform.entity.enums.FileType;
import com.example.convertform.service.IFileProcessService;
import com.example.convertform.service.impl.storage.FileStorageService;
import com.example.convertform.service.impl.storage.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileProcessService implements IFileProcessService {
    @Autowired
    private final FileReadService fileReadService;
    @Autowired
    private final FileValidateService fileValidateService;
    @Autowired
    private final FileConvertService fileConvertService;
    @Autowired
    private final FileWriteService fileWriteService;
    @Autowired
    private final FileStorageService fileStorageService;
    @Autowired
    private final UserService userService;

    @Override
    public ResponseEntity<?> processExcelFile(MultipartFile inputFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String name = authentication.getName();
        Integer uid = userService.getIdFromName(name);
        Integer orgFileId = 0;
        Map<String, Object> response = new HashMap<>();
        String orgName = inputFile.getOriginalFilename() != null ? inputFile.getOriginalFilename() : "org";

        System.out.println(name + " " + inputFile.getOriginalFilename() + " " + inputFile.getBytes().length);

        try {
            InputStream inputStream = new BufferedInputStream(inputFile.getInputStream());
            Object[] sheets = fileReadService.readInputFile(inputStream);
            System.out.println(Arrays.toString(sheets));

            ExcelFile orgFile = ExcelFile.builder()
                    .data(inputFile.getBytes())
                    .fileName(generateFileName(orgName))
                    .type(FileType.ORIGINAL)
                    .uid(uid)
                    .build();
            orgFileId = fileStorageService.saveFile(orgFile);

            List<ValidationErrorResponseDTO> validationErrorResponseDTOList = fileValidateService.validateSingleFieldData(sheets);
            fileValidateService.validateRelated(sheets, validationErrorResponseDTOList);

            if (!validationErrorResponseDTOList.isEmpty()) {
                //error file in bytes
                byte[] errorOutput = fileWriteService.writeErrorFile(validationErrorResponseDTOList);

                ExcelFile errorFile = ExcelFile.builder()
                        .data(errorOutput)
                        .fileName("error_rep_" + generateFileName(orgName))
                        .uid(uid)
                        .type(FileType.ERROR)
                        .orgFileId(orgFileId)
                        .build();

                fileStorageService.saveFile(errorFile);

                response.put("main_output", Map.of(
                        "fileId", errorFile.getId(),
                        "fileName", errorFile.getFileName()
                ));

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            }

            ConversionResult conversionResult = fileConvertService.convertData(sheets);

            //converted file in bytes
            byte[] output = fileWriteService.writeExcelFileDemo(conversionResult);
            byte[] outputSharedLib = fileWriteService.writeSharedLibFile(conversionResult);

            ExcelFile outputFile = ExcelFile.builder()
                    .data(output)
                    .fileName("converted_" + generateFileName(orgName))
                    .type(FileType.CONVERTED)
                    .uid(uid)
                    .orgFileId(orgFileId)
                    .build();
            fileStorageService.saveFile(outputFile);

            response.put("main_output", Map.of(
                    "fileId", outputFile.getId(),
                    "fileName", outputFile.getFileName()
            ));

            if (outputSharedLib != null) {
                ExcelFile sharedLibFile = ExcelFile.builder()
                        .data(outputSharedLib)
                        .fileName("shared_lib_" + generateFileName(orgName))
                        .type(FileType.CONVERTED)
                        .uid(uid)
                        .orgFileId(orgFileId)
                        .build();
                fileStorageService.saveFile(sharedLibFile);

                response.put("shared_lib_output", Map.of(
                        "fileId", sharedLibFile.getId(),
                        "fileName", sharedLibFile.getFileName()
                ));
            }

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Check blank cell again");
        }
    }

    private String generateFileName(String orgFileName) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMdd_HHmmss");
        String timestamp = now.format(dateTimeFormatter);

        int dotIndex = orgFileName.lastIndexOf(".");
        String baseName = (dotIndex == -1) ? orgFileName : orgFileName.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : orgFileName.substring(dotIndex);

        return baseName + "_" + timestamp + extension;
    }
}
