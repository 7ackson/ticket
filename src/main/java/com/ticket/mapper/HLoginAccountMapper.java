package com.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ticket.entity.SLoginAccount;
import com.ticket.pojo.bo.hloginaccount.HLoginAccountQueryBo;
import org.apache.ibatis.annotations.Param;

/**
 * @author imi
 * @description 针对表【h_login_account(系统-账户表)】的数据库操作Mapper
 * @createDate 2023-02-06 15:00:06
 * @Entity com.ticket.entity.HLoginAccount
 */
public interface HLoginAccountMapper extends BaseMapper<SLoginAccount> {


    IPage<SLoginAccount> getSLoginAccountSelect(IPage<SLoginAccount> page, @Param("param1") HLoginAccountQueryBo hLoginAccountQueryBo);

}




