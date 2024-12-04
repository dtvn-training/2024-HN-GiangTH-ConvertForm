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
import java.util.Arrays;
import java.util.List;

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
        System.out.println(name + " " + inputFile.getOriginalFilename() + " " + inputFile.getBytes().length);

        try {
            InputStream inputStream = new BufferedInputStream(inputFile.getInputStream());
            Object[] sheets = fileReadService.readInputFile(inputStream);
            System.out.println(Arrays.toString(sheets));

            ExcelFile orgFile = ExcelFile.builder()
                    .data(inputFile.getBytes())
                    .fileName(inputFile.getOriginalFilename())
                    .type(FileType.ORIGINAL)
                    .uid(uid)
                    .build();
            orgFileId = fileStorageService.saveFile(orgFile);

            List<ValidationErrorResponseDTO> validationErrorResponseDTOList = fileValidateService.validateSingleFieldData(sheets);
            fileValidateService.validateRelated(sheets, validationErrorResponseDTOList);

            if (!validationErrorResponseDTOList.isEmpty()) {
                //error file in bytes
                byte[] errorOutput = fileWriteService.writeErrorFile(validationErrorResponseDTOList);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDisposition(ContentDisposition
                        .attachment()
                        .filename("error_output.xlsx")
                        .build()
                );
                headers.setContentLength(errorOutput.length);

                ExcelFile errorFile = ExcelFile.builder()
                        .data(errorOutput)
                        .fileName("error_report.xlsx")
                        .uid(uid)
                        .type(FileType.ERROR)
                        .orgFileId(orgFileId)
                        .build();

                fileStorageService.saveFile(errorFile);

                return new ResponseEntity<>(errorOutput, headers, HttpStatus.EXPECTATION_FAILED);
            }

            ConversionResult conversionResult = fileConvertService.convertData(sheets);

            //converted file in bytes
            byte[] output = fileWriteService.writeExcelFileDemo(conversionResult);
            ExcelFile outputFile = ExcelFile.builder()
                    .data(output)
                    .fileName("test.xlsx")
                    .type(FileType.CONVERTED)
                    .uid(1)
                    .orgFileId(orgFileId)
                    .build();
            fileStorageService.saveFile(outputFile);
            byte[] outputSharedLib = fileWriteService.writeSharedLibFile(conversionResult);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition
                    .attachment()
                    .filename("convert_main_output.xlsx")
                    .build()
            );
            headers.setContentLength(output.length);

            return new ResponseEntity<>(output, headers, HttpStatus.OK);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Check blank cell again");
        }
    }
}
