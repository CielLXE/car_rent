package com.example.carrent.enums;

public enum ResultCodeEnum {
    SUCCESS(10000),
    FAIL(10001);

    private final int code;

    ResultCodeEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
