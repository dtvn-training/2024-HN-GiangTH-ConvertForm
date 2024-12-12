package com.example.convertform.sqlmapper;

import com.example.convertform.entity.RefreshToken;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface RefreshTokenMapper {
    @Insert("INSERT INTO refresh_token (userId, token, expiryDate) " +
            "VALUES (#{userId}, #{token}, #{expiryDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(RefreshToken refreshToken);

    @Select("SELECT * FROM refresh_token WHERE token = #{token}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "userId"),
            @Result(property = "token", column = "token"),
            @Result(property = "expiryDate", column = "expiryDate")
    })
    Optional<RefreshToken> findByToken(String token);

    @Delete("DELETE FROM refresh_token WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
}
