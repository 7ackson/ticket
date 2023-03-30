package com.ticket.pojo.po.account;

import java.io.Serializable;

/**
 * <p>
 * 系统-账户表
 * </p>
 *
 * @author imi
 * @since 2022-07-11
 */

public class AccountPushDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录账户id
     */
    private Long HLoginAccountId;

    /**
     * 单位id
     */
    private Long unitId;

    /**
     * 微信用户唯一标识，用于一键登录
     */
    private String wxOpenpid;

    /**
     * 人员id
     */
    private Long sysPersonnelId;

    /**
     * 人员姓名
     */
    private String personnelName;

    /**
     * 部门id
     */
    private Long sysDepartmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 合并（各级）名称，如：部门上级,部门下级
     */
    private String departmentMergeName;


}
