package com.ticket.common.exception;

/**
 * @description: 业务异常
 * @author: imi
 * @date: 2022/7/12 12:26
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
