package com.aqr.tca.utils;

public class StatusWork {
    private Integer code;
    private String text;

    StatusWork(Integer code, String text) {
        this.code = code;
        this.text = text;

    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
