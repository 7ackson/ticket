package com.ticket.service.impl;

import com.ticket.common.exception.ServiceException;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.common.service.JwtTokenService;
import com.ticket.common.utils.SecurityUtils;
import com.ticket.common.utils.StringUtils;
import com.ticket.entity.SLoginAccount;
import com.ticket.service.HLoginAccountService;
import com.ticket.service.SLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SLoginServiceImpl implements SLoginService {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private HLoginAccountService hLoginAccountService;

    @Override
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new ServiceException("账号密码不匹配");
            } else {
                throw new ServiceException(e.getMessage());
            }
        }
        LoginUserModel loginUser = (LoginUserModel) authentication.getPrincipal();
        // 生成token
        String token = jwtTokenService.createToken(loginUser);
        //记录登录信息
        recordLoginInfo(loginUser.getUserId());
        return token;
    }

    @Override
    public void recordLoginInfo(Long userId) {
        hLoginAccountService.updateLoginTimeById(userId);
    }

    @Transactional
    @Override
    public void resetPassword(Long hLoginAccountId, String passwordOld, String passwordNew) {
        //检查是否是超级管理员
        hLoginAccountService.checkUserAllowed(hLoginAccountId);

        SLoginAccount sLoginAccount = hLoginAccountService.getById(hLoginAccountId);
        if (StringUtils.isNull(sLoginAccount)) {
            throw new ServiceException("数据不存在");
        }

        boolean matchesPassword = SecurityUtils.matchesPassword(passwordOld, sLoginAccount.getPassword());
        if (!matchesPassword) {
            throw new ServiceException("原密码错误");
        }

        hLoginAccountService.updateUserPassword(hLoginAccountId, passwordNew);

        //删除redis登录信息
        LoginUserModel currentUser = SecurityUtils.getLoginUser();
        jwtTokenService.delLoginUser(currentUser.getLoginMark());
    }
}
