package com.ticket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ticket.common.apiresult.page.PageRequest;
import com.ticket.entity.SLoginAccount;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountAddBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountQueryBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountUpBo;

import java.util.List;

/**
 * @author imi
 * @description 针对表【h_login_account(系统-账户表)】的数据库操作Service
 * @createDate 2023-02-06 15:00:06
 */
public interface HLoginAccountService extends IService<SLoginAccount> {

    /**
     * 根据用户民获取信息
     *
     * @param userName
     * @return
     */
    SLoginAccount getHLoginAccountByUserName(String userName);

    IPage<SLoginAccount> findPage(PageRequest pageRequest, HLoginAccountQueryBo hLoginAccountQueryBo);


    IPage<SLoginAccount> listPage(PageRequest pageRequest, HLoginAccountQueryBo hLoginAccountQueryBo);

    Integer updateLoginTimeById(Long userId);

    /**
     * 校验用户是否可以操作院区
     *
     * @param hLoginAccountId
     * @return
     */
    void checkUserOperateServerArea(Long hLoginAccountId);

    /**
     * 校验用户是否为超级管理员
     *
     * @param hLoginAccountId
     */
    void checkUserIsSuperAdmin(Long hLoginAccountId, String operateMessage);

    void checkUserAllowed(Long hLoginAccountId);

    int updateUserPassword(Long hLoginAccountId, String password);

    Long checkUserNameUnique(String username, long id);

    int createUser(HLoginAccountAddBo paramBo);

    int editUser(HLoginAccountUpBo paramBo);

    int deleteUsers(List<Long> ids);

    int changeStatus(Long id);
}
