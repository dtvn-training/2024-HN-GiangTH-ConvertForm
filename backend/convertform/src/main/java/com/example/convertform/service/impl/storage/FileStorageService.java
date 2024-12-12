package com.example.convertform.service.impl.storage;

import com.example.convertform.entity.ExcelFile;
import com.example.convertform.entity.enums.FileType;
import com.example.convertform.sqlmapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    @Autowired
    private final FileMapper fileMapper;

    public Integer saveFile(ExcelFile file) {
        if (file.getType() == FileType.ORIGINAL) fileMapper.insertOrgFile(file);
        else fileMapper.insertConvertFile(file);
        System.out.println("Save file success!!!!");
        return file.getId();
    }

    public ResponseEntity<?> getUserHistory(Integer uid) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fileMapper.getFileHistoryByUid(uid));
    }

    public ResponseEntity<?> downloadFileById(Integer id, String fileName) {
        byte[] requestFileData = fileMapper.getFileDataById(id).getData();

        if (!fileName.contains(".xlsx")) fileName += ".xlsx";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename(fileName)
                .build()
        );
        headers.setContentLength(requestFileData.length);

        System.out.println("Sent!!");

        return new ResponseEntity<>(requestFileData, headers, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteFile(Integer fid) {
        fileMapper.deleteFile(fid);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted");
    }
}
