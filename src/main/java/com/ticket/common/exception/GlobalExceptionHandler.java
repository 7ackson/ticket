package com.ticket.common.exception;


import com.ticket.common.apiresult.CommonResult;
import com.ticket.common.apiresult.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 全局统一异常处理
 * @author: ye wei
 * @create: 2022/07/08 10:54
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 重写404错误
     *
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handleError() {
        return CommonResult.generalResult(ResultCodeEnum.NOT_FOND, "未找到请求资源", null, "路径不存在，请检查路径是否正确");
    }

    /**
     * 参数错误 400(bean 校验)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handServletIllegalExceptionBean(BindException e) {
        log.error("BindException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED_PARAM_VALID, "", null, e.getMessage());
    }

    /**
     * 参数错误 400
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handServletIllegalException(ServletRequestBindingException e) {
        log.error("ServletRequestBindingException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED_PARAM_VALID, "", null, e.getMessage());
    }

    /**
     * 参数错误 400
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED_PARAM_VALID, "", null, e.getMessage());
    }

    /**
     * 参数错误 400 手动
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handServletIllegalException(IllegalException e) {
        log.error("IllegalException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED_PARAM_VALID, "", null, e.getMessage());
    }

    /**
     * 暂未登录或token已经过期 AF401
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handUnauthorizedException(AuthenticationException e) {
        log.error("AuthenticationException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.UNAUTHORIZED, "", null, e.getMessage());
    }

    /**
     * 未找到资源 404
     *
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handNotFoundException(NotFoundException e) {
        log.error("NotFoundException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.NOT_FOND, "", null, e.getMessage());
    }

    /**
     * 请求方法不对 405
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handServletIllegalException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.METHOD_NOT_ALLOWED, "", null, e.getMessage());
    }

    /**
     * 资源已存在 409
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handIllegalException(ConflictException e) {
        log.error("ConflictException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.CONFLICT, "", null, e.getMessage());
    }

    /**
     * 默认异常 500(默认)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult defaultErrorHandler(Throwable throwable) {
        log.error("异常>>", throwable);

        String message = "", description = "";
        if (throwable.getClass().equals(SQLIntegrityConstraintViolationException.class)) {
            message = "操作失败(已存在相同数据)";
            description = "有关联数据存在，违反唯一约束条件";
        } else if (throwable.getClass().equals(ConstraintViolationException.class)) {
            message = "操作失败(数据已被其他功能使用)";
            description = "有关联数据存在，违反外键约束条件";
        } else if (throwable.getClass().equals(DataIntegrityViolationException.class)) {
            //org.springframework.dao.DataIntegrityViolationException
            message = "操作失败(存在相同数据,或数据被其他模块使用)";
            description = "违反数据库约束条件";
        } else if (throwable.getClass().equals(DuplicateKeyException.class)) {
            //org.springframework.dao.DuplicateKeyException
            message = "操作失败(存在相同数据,或数据被其他模块使用)";
            description = "违反数据库约束条件";
        }

        return CommonResult.generalResult(ResultCodeEnum.ERROR_500, message, null, description);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException > {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return CommonResult.generalResult(ResultCodeEnum.FAILED_PARAM_VALID, "", errorMap, e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handNotServiceException(ServiceException e) {
        log.error("ServiceException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED, "", null, e.getMessage());
    }

    @ExceptionHandler(UtilException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult handNotUtilException(UtilException e) {
        log.error("UtilException > {}", e.getMessage());
        return CommonResult.generalResult(ResultCodeEnum.FAILED, "", null, e.getMessage());
    }

}
