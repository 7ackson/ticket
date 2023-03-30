package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统-账户表
 * @TableName s_login_account
 */
@TableName(value ="s_login_account")

public class SLoginAccount implements Serializable {
    /**
     * 登录账户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 院区表ID
     */
    @TableField("h_server_area_id")
    @JsonSerialize(using = ToStringSerializer.class)
    @Schema(name = "hServerAreaId", description = "院区表ID")
    private Long hServerAreaId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 角色：1-平台管理员， 2-普通用户
     */
    @TableField(value = "role")
    private Integer role;

    /**
     * 状态：0-不可用， 1-可用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_datetime")
    private Date lastLoginDatetime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    //展示数据
    @TableField(exist = false)
    private String serverAreaName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getServerAreaName() {
        return serverAreaName;
    }

    public void setServerAreaName(String serverAreaName) {
        this.serverAreaName = serverAreaName;
    }
}