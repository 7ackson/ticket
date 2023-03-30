package com.ticket.common.utils;

import com.ticket.common.exception.ServiceException;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.enums.LoginAccountRoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: 安全服务工具类
 * @author: imi
 * @date: 2022/7/12 12:11
 */
public class SecurityUtils {

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new ServiceException("获取用户ID异常");
        }
    }

    /**
     * 获取院区ID
     **/
    public static Long gethServerAreaId() {
        try {
            return getLoginUser().gethServerAreaId();
        } catch (Exception e) {
            throw new ServiceException("获取院区ID异常");
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new ServiceException("获取用户账户异常");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUserModel getLoginUser() {
        try {
            return (LoginUserModel) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 验证角色，限制院区
     *
     * @param queryHServerAreaId
     * @return
     */
    public static Long convertServerAreaId(Long queryHServerAreaId) {
        Integer role = getLoginUser().getUser().getRole();
        if (!role.equals(LoginAccountRoleEnum.CHAO_JI_GUAN_LI_YUAN.getCode())) {
            return gethServerAreaId();
        } else {
            //if (queryHServerAreaId == null) {
            //    return gethServerAreaId();
            //}
        }
        return queryHServerAreaId;
    }

}
