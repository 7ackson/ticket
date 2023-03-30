package com.ticket.common.exception;

/**
 * @description: 非法参数异常捕获类
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
public class IllegalException extends  RuntimeException {
    public IllegalException(String message){
        super(message);
    }

}
