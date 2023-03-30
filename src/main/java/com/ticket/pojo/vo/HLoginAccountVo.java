package com.ticket.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;


public class HLoginAccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 院区表ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "hServerAreaId", description = "院区表ID")
    private Long hServerAreaId;

    private String serverAreaName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色：1-平台管理员， 2-普通用户
     */
    private Integer role;

    /**
     * 状态：0-不可用， 1-可用
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private Date lastLoginDatetime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    public Long gethServerAreaId() {
        return hServerAreaId;
    }

    public void sethServerAreaId(Long hServerAreaId) {
        this.hServerAreaId = hServerAreaId;
    }

    public String getServerAreaName() {
        return serverAreaName;
    }

    public void setServerAreaName(String serverAreaName) {
        this.serverAreaName = serverAreaName;
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

    public Date getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Date lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
