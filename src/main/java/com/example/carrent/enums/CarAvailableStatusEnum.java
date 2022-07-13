package com.example.carrent.enums;

import lombok.Getter;

@Getter
public enum CarAvailableStatusEnum {

    unavailable(0, "unavailable"),
    available(1, "available"),
    ;
    private int code;
    private String msg;

    CarAvailableStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
