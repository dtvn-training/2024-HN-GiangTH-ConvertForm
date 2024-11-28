package com.example.convertform.service.impl;

import com.example.convertform.dto.output.ConversionResult;
import com.example.convertform.dto.response.ValidationErrorResponseDTO;
import com.example.convertform.service.IFileProcessService;
import com.example.convertform.service.impl.repository.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Override
    public ResponseEntity<?> processExcelFile(MultipartFile inputFile) {
        try {
            Object[] sheets = fileReadService.readInputFileDemo();

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

                return new ResponseEntity<>(errorOutput, headers, HttpStatus.EXPECTATION_FAILED);
            }

            ConversionResult conversionResult = fileConvertService.convertData(sheets);

            //converted file in bytes
            byte[] output = fileWriteService.writeExcelFileDemo(conversionResult);
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
