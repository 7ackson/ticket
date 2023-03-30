package com.ticket.pojo.bo.hloginaccount;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class HLoginAccountAddBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 院区表ID
     */
    @Schema(name = "hServerAreaId", description = "院区表ID")
    private Long hServerAreaId;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 角色：1-超级管理员， 2-院区管理员
     */
    @NotNull(message = "角色不能为空")
    private Integer role;

    /**
     * 状态：0-不可用， 1-可用
     */
    @NotNull(message = "状态不能为空")
    private Integer status;

    /**
     * 备注
     */
    private String remark;


    public Long gethServerAreaId() {
        return hServerAreaId;
    }

    public void sethServerAreaId(Long hServerAreaId) {
        this.hServerAreaId = hServerAreaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
