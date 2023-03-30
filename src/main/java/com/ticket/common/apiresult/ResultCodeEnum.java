package com.ticket.common.apiresult;

/**
 * @description: 结果码枚举类
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public enum ResultCodeEnum implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS("200", "操作成功"),
    NO_CONTENT("204", "无数据"),
    ERROR_500("500", "系统错误，请稍后再试"),
    FAILED_PARAM_VALID("400", "参数错误"),
    FAILED("401", "操作失败"),
    UNAUTHORIZED("AF401", "暂未登录或token已经过期"),
    FORBIDDEN("402", "没有相关权限"),
    NOT_FOND("404", "请求资源不存在"),
    METHOD_NOT_ALLOWED("405", "请求方法不对"),
    CONFLICT("409", "资源已存在");


    /**
     * 返回结果码
     */
    private String code;

    /**
     * 返回信息内容
     */
    private String message;

    /**
     * 私有类构造器
     *
     * @param code    结果码
     * @param message 信息内容
     */

    ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回结果码
     *
     * @return 结果码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 返回结果信息
     *
     * @return 结果信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
