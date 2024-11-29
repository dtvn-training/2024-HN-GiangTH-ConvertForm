package com.example.convertform.sqlmapper;

import com.example.convertform.dto.response.FileHistoryDTO;
import com.example.convertform.entity.ExcelFile;
import com.example.convertform.entity.FileWith2Params;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.ByteArrayTypeHandler;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO Files (uid, fileName, data, type, orgFileId) VALUES (#{uid}, #{fileName}, #{data}, #{type}, #{orgFileId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFile(ExcelFile file);

    @Select("SELECT id, fileName, type, create_at, orgFileId FROM Files WHERE uid = #{uid} ORDER BY time DESC")
    List<FileHistoryDTO> getHistoryByUserId(Integer uid);

    @Select("SELECT fileName, data FROM Files WHERE id = #{id}")
    @Results({
            @Result(property = "fileName", column = "fileName", javaType = String.class),
            @Result(property = "data", column = "data", typeHandler = ByteArrayTypeHandler.class)
    })
    FileWith2Params getFileDataById(Integer id);
}
