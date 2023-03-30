package com.ticket.pojo.bo.hloginaccount;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


public class HLoginAccountQueryBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 院区表ID
     */
    @Schema(name = "hServerAreaId", description = "院区表ID")
    private Long hServerAreaId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 角色：1-平台管理员， 2-普通用户
     */
    @Schema(description = "角色：1-平台管理员， 2-普通用户")
    private Integer role;

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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
