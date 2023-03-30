package com.ticket.common.apiresult;

import java.io.Serializable;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/27 16:45
 */
public class CommonResultPo implements Serializable {
    private Object code;
    private String message;
    private Object data;
    private String description;

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

