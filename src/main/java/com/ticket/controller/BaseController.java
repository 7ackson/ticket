package com.ticket.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ticket.common.apiresult.page.PageResult;
import com.ticket.common.security.model.LoginUserModel;
import com.ticket.common.utils.SecurityUtils;

/**
 * @description: web层通用数据处理
 * @author: yewei
 * @date: 2022/7/12 16:34
 */
public class BaseController {

    /**
     * 获取分页查询返回数据集
     *
     * @param pageInfo
     * @return
     */
    protected PageResult getPageTable(IPage<?> pageInfo) {
        return new PageResult(pageInfo);
    }

    /**
     * 获取用户缓存信息
     */
    public LoginUserModel getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 获取登录用户id
     */
    public Long getUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取登录用户名
     */
    public String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取登录院区ID
     */
    public Long gethServerAreaId() {
        return getLoginUser().gethServerAreaId();
    }


}
