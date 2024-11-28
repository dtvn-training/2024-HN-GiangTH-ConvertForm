package com.example.convertform.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String userName;
    private String passwordHashed;
}
