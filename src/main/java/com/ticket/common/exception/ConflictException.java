package com.ticket.common.exception;

/**
 * @description: 资源已存在异常捕获
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}