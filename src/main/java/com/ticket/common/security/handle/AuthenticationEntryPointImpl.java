package com.ticket.common.security.handle;

import com.alibaba.fastjson2.JSON;
import com.ticket.common.apiresult.CommonResult;
import com.ticket.common.apiresult.ResultCodeEnum;
import com.ticket.common.utils.ServletUtils;
import com.ticket.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/12 11:04
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        //String msg = StringUtils.format("认证失败。无法访问请求：{}", request.getRequestURI());
        String msg = StringUtils.format("认证失败，无法访问请求！");
        ServletUtils.renderString(response, JSON.toJSONString(CommonResult.generalMessageResult(ResultCodeEnum.UNAUTHORIZED, msg)));
    }
}
