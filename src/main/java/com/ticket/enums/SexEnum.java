package com.ticket.enums;


public enum SexEnum {

    MAN(0, "男"),
    WOMAN(1, "女");

    private int code;

    private String mark;

    SexEnum(int code, String mark) {
        this.code = code;
        this.mark = mark;
    }

    public int getCode() {
        return code;
    }

    public String getMark() {
        return mark;
    }
}
