package com.ticket.common.exception;

/**
 * @description: 未找到资源异常捕获类
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
