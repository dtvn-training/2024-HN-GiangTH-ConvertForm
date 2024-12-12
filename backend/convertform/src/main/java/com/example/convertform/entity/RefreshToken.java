package com.example.convertform.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class RefreshToken {
    private long id;
    private Integer userId;
    private String token;
    private Instant expiryDate;
}
