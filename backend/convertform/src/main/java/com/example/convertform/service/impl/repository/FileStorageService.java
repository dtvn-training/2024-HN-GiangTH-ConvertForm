package com.example.convertform.service.impl.repository;

import com.example.convertform.entity.ExcelFile;
import com.example.convertform.sqlmapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    @Autowired
    private final FileMapper fileMapper;

    public void saveFile() {

    }

    public ResponseEntity<byte[]> downloadFileById(Integer id, String fileName) {
        byte[] requestFileData = fileMapper.getFileDataById(id);

        if (!fileName.contains(".xlsx")) fileName += ".xlsx";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename(fileName)
                .build()
        );
        headers.setContentLength(requestFileData.length);

        return new ResponseEntity<>(requestFileData, headers, HttpStatus.OK);
    }
}
