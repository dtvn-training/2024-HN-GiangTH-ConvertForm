package com.example.convertform.sqlmapper;

import com.example.convertform.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM Users WHERE uid = #{id}")
    User getUserById(Integer id);

    @Insert("INSERT INTO Users (userName, passwordHash) VALUES (#{userName}, #{passwordHashed})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

}
