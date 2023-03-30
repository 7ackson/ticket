package com.ticket.pojo.bo.hloginaccount;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class HLoginAccountUpBo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 登录账户id
     */
    @NotNull(message = "账户ID不能为空")
    @Range(min = 1)
    @Schema(name = "hLoginAccountId", description = "院区表ID")
    private Long hLoginAccountId;

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
    private String password;

    /**
     * 角色：1-平台管理员， 2-普通用户
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

    public Long gethLoginAccountId() {
        return hLoginAccountId;
    }

    public void sethLoginAccountId(Long hLoginAccountId) {
        this.hLoginAccountId = hLoginAccountId;
    }

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

    @Override
    public String toString() {
        return "HLoginAccountUpBo{" +
                "hLoginAccountId=" + hLoginAccountId +
                ", hServerAreaId=" + hServerAreaId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}
