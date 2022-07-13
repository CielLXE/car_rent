package com.example.carrent.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    normal(1, "normal_user"),
    admin(2, "administrator"),
    ;
    private int code;
    private String msg;

    UserRoleEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
