package com.ticket.service;


public interface SLoginService {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 记录登录信息
     *
     * @param userId
     */
    void recordLoginInfo(Long userId);

    /**
     * 重置当前用户密码
     * @param hLoginAccountId
     * @param passwordOld
     * @param passwordNew
     */
    void resetPassword(Long hLoginAccountId, String passwordOld, String passwordNew);

}
