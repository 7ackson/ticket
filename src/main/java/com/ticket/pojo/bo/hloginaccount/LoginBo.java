package com.ticket.pojo.bo.hloginaccount;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @description: 登录入参
 * @author: ye wei
 * @create: 2020/07/15 09:29
 */

public class LoginBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "账号不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String userPassword;

    public LoginBo() {
    }

    public LoginBo(@NotEmpty(message = "账号不能为空") String userName, @NotEmpty(message = "密码不能为空") String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
