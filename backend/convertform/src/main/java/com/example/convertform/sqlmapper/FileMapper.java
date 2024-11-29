package com.example.convertform.sqlmapper;

import com.example.convertform.dto.response.FileHistoryDTO;
import com.example.convertform.entity.ExcelFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO Files (uid, fileName, data, type, orgFileId) VALUES (#{uid}, #{fileName}, #{data}, #{type}, #{orgFileId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFile(ExcelFile file);

    @Select("SELECT id, fileName, type, create_at, orgFileId FROM Files WHERE uid = #{uid} ORDER BY time DESC")
    List<FileHistoryDTO> getHistoryByUserId(Integer uid);

    @Select("SELECT data FROM Files WHERE id = #{id}")
    byte[] getFileDataById(Integer id);
}
