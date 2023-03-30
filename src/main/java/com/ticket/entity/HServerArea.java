package com.ticket.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 院区表
 * @TableName h_server_area
 */
@TableName(value ="h_server_area")

public class HServerArea implements Serializable {
    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 院区(ID)编码
     */
    @TableField(value = "server_area_code")
    private String serverAreaCode;

    /**
     * 院区编码
     */
    @TableField(value = "server_area_encode")
    private String serverAreaEncode;

    /**
     * 院区名称
     */
    @TableField(value = "server_area_name")
    private String serverAreaName;

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

    public HServerArea() {
    }

    public HServerArea(Long id, String serverAreaCode, String serverAreaEncode, String serverAreaName, String remark, Date createTime, Date modifyTime) {
        this.id = id;
        this.serverAreaCode = serverAreaCode;
        this.serverAreaEncode = serverAreaEncode;
        this.serverAreaName = serverAreaName;
        this.remark = remark;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerAreaCode() {
        return serverAreaCode;
    }

    public void setServerAreaCode(String serverAreaCode) {
        this.serverAreaCode = serverAreaCode;
    }

    public String getServerAreaEncode() {
        return serverAreaEncode;
    }

    public void setServerAreaEncode(String serverAreaEncode) {
        this.serverAreaEncode = serverAreaEncode;
    }

    public String getServerAreaName() {
        return serverAreaName;
    }

    public void setServerAreaName(String serverAreaName) {
        this.serverAreaName = serverAreaName;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HServerArea that = (HServerArea) o;
        return Objects.equals(id, that.id) && Objects.equals(serverAreaCode, that.serverAreaCode) && Objects.equals(serverAreaEncode, that.serverAreaEncode) && Objects.equals(serverAreaName, that.serverAreaName) && Objects.equals(remark, that.remark) && Objects.equals(createTime, that.createTime) && Objects.equals(modifyTime, that.modifyTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serverAreaCode, serverAreaEncode, serverAreaName, remark, createTime, modifyTime);
    }

    @Override
    public String toString() {
        return "HServerArea{" +
                "id=" + id +
                ", serverAreaCode='" + serverAreaCode + '\'' +
                ", serverAreaEncode='" + serverAreaEncode + '\'' +
                ", serverAreaName='" + serverAreaName + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}