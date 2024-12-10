package com.example.convertform.sqlmapper;

import com.example.convertform.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM Users WHERE uid = #{id}")
    Optional<User> getUserById(Integer id);

    @Select("SELECT * FROM Users WHERE username = #{name}")
    User getUserByName(String name);

    @Insert("INSERT INTO Users (username, password, roles) VALUES (#{username}, #{password}, #{roles})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

}
