package com.ticket.pojo.bo.hloginaccount;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


public class HLoginAccountDelBo {

    private static final long serialVersionUID = 1L;

    /**
     * 人员ids
     */
    @NotNull
    @Size(min = 1, message = "值错误")
    private List<Long> ids;

    public HLoginAccountDelBo() {
    }

    public HLoginAccountDelBo(@NotNull @Size(min = 1, message = "值错误") List<Long> ids) {
        this.ids = ids;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
