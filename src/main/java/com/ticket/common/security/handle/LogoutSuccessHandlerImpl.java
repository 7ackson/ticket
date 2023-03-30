package com.ticket.common.security.handle;

import com.alibaba.fastjson2.JSON;
import com.ticket.common.apiresult.CommonResult;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.common.service.JwtTokenService;
import com.ticket.common.utils.ServletUtils;
import com.ticket.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: imi
 * @date: 2022/7/12 11:13
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUserModel loginUserModel = jwtTokenService.getLoginUser(request, true);
        if (StringUtils.isNotNull(loginUserModel)) {
            //String userName = loginUserModel.getUsername();
            // 删除用户缓存记录
            jwtTokenService.delLoginUser(loginUserModel.getLoginMark());
            // 记录用户退出日志
            //AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(CommonResult.success(null, "退出成功")));

    }
}
