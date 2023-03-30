package com.ticket.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.common.apiresult.page.PageRequest;
import com.ticket.common.exception.ServiceException;
import com.ticket.common.utils.SecurityUtils;
import com.ticket.common.utils.StringUtils;
import com.ticket.entity.SLoginAccount;
import com.ticket.enums.LoginAccountRoleEnum;
import com.ticket.mapper.HLoginAccountMapper;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountAddBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountQueryBo;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountUpBo;
import com.ticket.service.HLoginAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author imi
 * @description 针对表【h_login_account(系统-账户表)】的数据库操作Service实现
 * @createDate 2023-02-06 15:00:06
 */
@Service
public class HLoginAccountServiceImpl extends ServiceImpl<HLoginAccountMapper, SLoginAccount> implements HLoginAccountService {

    @Override
    public SLoginAccount getHLoginAccountByUserName(String userName) {
        LambdaQueryWrapper<SLoginAccount> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SLoginAccount::getUsername, userName);
        return baseMapper.selectOne(lqw);
    }

    @Override
    public IPage<SLoginAccount> findPage(PageRequest pageRequest, HLoginAccountQueryBo hLoginAccountQueryBo) {
        Page<SLoginAccount> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        LambdaQueryWrapper<SLoginAccount> lqw = new LambdaQueryWrapper<>();
        if (StringUtils.isNotNull(hLoginAccountQueryBo.gethServerAreaId())) {
            lqw.eq(SLoginAccount::gethServerAreaId, hLoginAccountQueryBo.gethServerAreaId());
        }
        if (StringUtils.isNotEmpty(hLoginAccountQueryBo.getUsername())) {
            lqw.like(SLoginAccount::getUsername, hLoginAccountQueryBo.getUsername());
        }
        if (StringUtils.isNotNull(hLoginAccountQueryBo.getRole())) {
            lqw.eq(SLoginAccount::getRole, hLoginAccountQueryBo.getRole());
        }
        return baseMapper.selectPage(page, lqw);
    }

    @Override
    public IPage<SLoginAccount> listPage(PageRequest pageRequest, HLoginAccountQueryBo hLoginAccountQueryBo) {
        Page<SLoginAccount> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        return baseMapper.getSLoginAccountSelect(page, hLoginAccountQueryBo);
    }

    @Override
    public Integer updateLoginTimeById(Long userId) {
        SLoginAccount sLoginAccount = baseMapper.selectById(userId);
        if (sLoginAccount != null) {
            LambdaUpdateWrapper<SLoginAccount> luq = new LambdaUpdateWrapper<>();
            luq.eq(SLoginAccount::getId, userId);
            luq.set(SLoginAccount::getLastLoginDatetime, new Date());
            return baseMapper.update(null, luq);
        }
        return 0;
    }

    @Override
    public void checkUserOperateServerArea(Long hLoginAccountId) {
        SLoginAccount sLoginAccount = baseMapper.selectById(hLoginAccountId);
        if (sLoginAccount == null) {
            throw new ServiceException("获取用户信息失败，重新登录再操作");
        }
        if (sLoginAccount.getRole() != LoginAccountRoleEnum.CHAO_JI_GUAN_LI_YUAN.getCode()) {
            throw new ServiceException("无权限管理院区数据");
        }
    }

    @Override
    public void checkUserIsSuperAdmin(Long hLoginAccountId, String operateMessage) {
        boolean isSuperAdmin = SecurityUtils.isAdmin(hLoginAccountId);
        if (isSuperAdmin) {
            throw new ServiceException(operateMessage);
        }
        SLoginAccount sLoginAccount = baseMapper.selectById(hLoginAccountId);
        if (sLoginAccount == null) {
            throw new ServiceException("获取用户信息失败，重新登录再操作");
        }
        if (sLoginAccount.getRole() != LoginAccountRoleEnum.CHAO_JI_GUAN_LI_YUAN.getCode()) {
            throw new ServiceException(operateMessage);
        }
    }

    @Override
    public void checkUserAllowed(Long hLoginAccountId) {
        boolean isSuperAdmin = SecurityUtils.isAdmin(hLoginAccountId);
        if (isSuperAdmin) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    @Override
    public int updateUserPassword(Long hLoginAccountId, String password) {
        LambdaUpdateWrapper<SLoginAccount> luq = new LambdaUpdateWrapper<>();
        luq.eq(SLoginAccount::getId, hLoginAccountId);
        luq.set(SLoginAccount::getPassword, SecurityUtils.encryptPassword(password));
        luq.set(SLoginAccount::getLastLoginDatetime, new Date());
        luq.set(SLoginAccount::getModifyTime, new Date());
        return baseMapper.update(null, luq);
    }

    @Override
    public Long checkUserNameUnique(String username, long id) {
        LambdaQueryWrapper<SLoginAccount> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SLoginAccount::getUsername, username);
        if (id > 0L) {
            lqw.ne(SLoginAccount::getId, id);
        }
        return baseMapper.selectCount(lqw);
    }

    @Transactional
    @Override
    public int createUser(HLoginAccountAddBo paramBo) {
        SLoginAccount sLoginAccount = BeanUtil.copyProperties(paramBo, SLoginAccount.class);
        sLoginAccount.setPassword(SecurityUtils.encryptPassword(sLoginAccount.getPassword()));
        sLoginAccount.setCreateTime(new Date());
        return baseMapper.insert(sLoginAccount);
    }

    @Transactional
    @Override
    public int editUser(HLoginAccountUpBo paramBo) {
        SLoginAccount sLoginAccount = baseMapper.selectById(paramBo.gethLoginAccountId());
        if (sLoginAccount == null) {
            throw new ServiceException("数据不存在");
        }

        LambdaUpdateWrapper<SLoginAccount> luq = new LambdaUpdateWrapper<>();
        luq.eq(SLoginAccount::getId, paramBo.gethLoginAccountId());

        luq.set(SLoginAccount::gethServerAreaId, paramBo.gethServerAreaId());
        luq.set(SLoginAccount::getUsername, paramBo.getUsername());
        if (StringUtils.isNotEmpty(paramBo.getPassword())) {
            luq.set(SLoginAccount::getPassword, SecurityUtils.encryptPassword(paramBo.getPassword()));
        }
        luq.set(SLoginAccount::getRole, paramBo.getRole());
        luq.set(SLoginAccount::getStatus, paramBo.getStatus());
        luq.set(SLoginAccount::getRemark, paramBo.getRemark());
        luq.set(SLoginAccount::getModifyTime, new Date());
        return baseMapper.update(null, luq);
    }

    @Transactional
    @Override
    public int deleteUsers(List<Long> userIds) {
        for (Long userId : userIds) {
            if (CollectionUtil.isEmpty(userIds)) {
                throw new ServiceException("参数值错误:" + userId);
            }
            checkUserAllowed(userId);
        }
        return baseMapper.deleteBatchIds(userIds);
    }

    @Transactional
    @Override
    public int changeStatus(Long id) {
        SLoginAccount sLoginAccount = baseMapper.selectById(id);
        if (sLoginAccount == null) {
            throw new ServiceException("数据不存在");
        }

        LambdaUpdateWrapper<SLoginAccount> luw = new LambdaUpdateWrapper<>();
        luw.eq(SLoginAccount::getId, id);
        Integer status = sLoginAccount.getStatus();
        if (status == 0) {
            luw.set(SLoginAccount::getStatus, 1);
        } else {
            luw.set(SLoginAccount::getStatus, 0);
        }
        luw.set(SLoginAccount::getModifyTime, new Date());
        return baseMapper.update(null, luw);
    }
}




