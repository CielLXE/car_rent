package com.example.carrent.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    private String password;

    /**
     * 1: normal user 2: admin
     */
    private Byte userRole;

}