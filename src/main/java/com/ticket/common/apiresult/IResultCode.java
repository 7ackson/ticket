package com.ticket.common.apiresult;

/**
 * @description: 错误码
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public interface IResultCode {
    /**
     * 获取结果码
     *
     * @return 结果码
     */
    String getCode();

    /**
     * 返回结果信息
     *
     * @return 结果信息
     */
    String getMessage();
}
