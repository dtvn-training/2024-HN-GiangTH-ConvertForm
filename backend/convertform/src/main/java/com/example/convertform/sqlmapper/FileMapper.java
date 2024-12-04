package com.example.convertform.sqlmapper;

import com.example.convertform.dto.response.FileHistoryDTO;
import com.example.convertform.dto.response.FileInfoDTO;
import com.example.convertform.entity.ExcelFile;
import com.example.convertform.dto.response.FileWithDataDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.ByteArrayTypeHandler;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO Files (uid, fileName, data, type, orgFileId) VALUES (#{uid}, #{fileName}, #{data}, #{type}, #{orgFileId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertConvertFile(ExcelFile file);

    @Insert("INSERT INTO Files (uid, fileName, data, type) VALUES (#{uid}, #{fileName}, #{data}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrgFile(ExcelFile file);

    @Select("SELECT DISTINCT f1.id as original_id, " +
            "f1.fileName as original_fileName, " +
            "f1.type as original_type, " +
            "f1.create_at as original_createAt " +
            "FROM `Files` f1 " +
            "WHERE f1.type = 'ORIGINAL' AND f1.uid = #{uid} " +
            "ORDER BY f1.create_at DESC")
    @Results({
            @Result(property = "originalFile.fileId", column = "original_id"),
            @Result(property = "originalFile.fileName", column = "original_fileName"),
            @Result(property = "originalFile.type", column = "original_type"),
            @Result(property = "originalFile.createAt", column = "original_createAt"),
            @Result(property = "relatedFiles", column = "original_id",
                    javaType = List.class,
                    many = @Many(select = "getRelatedFiles"))
    })
    List<FileHistoryDTO> getFileHistoryByUid(@Param("uid") Integer uid);

    @Select("SELECT id, fileName, type, create_at as createAt " +
            "FROM `Files` " +
            "WHERE orgFileId = #{originalId} " +
            "ORDER BY create_at ASC")
    @Results({
            @Result(property = "fileId", column = "id"),
            @Result(property = "fileName", column = "fileName"),
            @Result(property = "type", column = "type"),
            @Result(property = "createAt", column = "createAt")
    })
    List<FileInfoDTO> getRelatedFiles(@Param("originalId") Integer originalId);

    @Select("SELECT fileName, data FROM Files WHERE id = #{id}")
    @Results({
            @Result(property = "fileName", column = "fileName", javaType = String.class),
            @Result(property = "data", column = "data", typeHandler = ByteArrayTypeHandler.class)
    })
    FileWithDataDTO getFileDataById(Integer id);
}
