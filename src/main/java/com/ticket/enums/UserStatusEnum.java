package com.ticket.enums;

/**
 * @description:
 * @author: yewei
 * @date: 2022/7/12 14:22
 */
public enum UserStatusEnum {
    ENABLE(1, "可用"),
    DISABLE(0, "停用");

    private final int code;
    private final String mark;

    UserStatusEnum(int code, String mark) {
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
