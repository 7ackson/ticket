package com.ticket.pojo.bo;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 系统-账户表
 */

public class ReSetPasswordBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 密码
     */
    @NotEmpty(message = "原始密码不能为空")
    private String passwordOld;

    /**
     * 密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String passwordNew;

    public ReSetPasswordBo() {
    }

    public ReSetPasswordBo(@NotEmpty(message = "原始密码不能为空") String passwordOld, @NotEmpty(message = "新密码不能为空") String passwordNew) {
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
}
