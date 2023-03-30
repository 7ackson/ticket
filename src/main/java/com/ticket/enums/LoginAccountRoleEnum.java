package com.ticket.enums;

/**
 * @description:
 * @author: yewei
 * @date: 2023/2/13 11:27
 */
public enum LoginAccountRoleEnum {
    CHAO_JI_GUAN_LI_YUAN(1,"超级管理员"),
    YUAN_QU_GUAN_LI_YUAN(2,"院区管理员");

    private final Integer code;
    private final String mark;

    public Integer getCode() {
        return code;
    }

    public String getMark() {
        return mark;
    }

    LoginAccountRoleEnum(Integer code, String mark) {
        this.code = code;
        this.mark = mark;
    }
}
