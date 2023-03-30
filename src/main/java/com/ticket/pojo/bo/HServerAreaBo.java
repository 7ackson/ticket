package com.ticket.pojo.bo;

public class HServerAreaBo {

    /**
     * 院区编码
     */
    private String  serverAreaCode;

    /**
     * 院区名称
     */
    private String serverAreaName;

    public HServerAreaBo() {
    }

    public HServerAreaBo(String serverAreaCode, String serverAreaName) {
        this.serverAreaCode = serverAreaCode;
        this.serverAreaName = serverAreaName;
    }

    public String getServerAreaCode() {
        return serverAreaCode;
    }

    public void setServerAreaCode(String serverAreaCode) {
        this.serverAreaCode = serverAreaCode;
    }

    public String getServerAreaName() {
        return serverAreaName;
    }

    public void setServerAreaName(String serverAreaName) {
        this.serverAreaName = serverAreaName;
    }
}
