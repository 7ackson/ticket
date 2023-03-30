package com.ticket.common.security.handle;

import com.ticket.common.exception.ServiceException;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.common.utils.StringUtils;
import com.ticket.entity.SLoginAccount;
import com.ticket.enums.UserStatusEnum;
import com.ticket.service.HLoginAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description: 用户验证处理
 * @author: imi
 * @date: 2022/7/12 14:20
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private HLoginAccountService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SLoginAccount user = userService.getHLoginAccountByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        //else if (1 != user.getRole()) {
        //    log.info("登录用户：{} 非平台管理员.", username);
        //    throw new ServiceException("对不起，您的账号：" + username + " 非平台管理员");
        //}
        else if (UserStatusEnum.DISABLE.getCode() == user.getStatus()) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SLoginAccount user) {
        return new LoginUserModel(user.getId(), user.gethServerAreaId(), user, null);
    }
}
