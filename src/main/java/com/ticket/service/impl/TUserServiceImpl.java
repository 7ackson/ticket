package com.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.entity.TUser;
import com.ticket.service.TUserService;
import com.ticket.mapper.TUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 30669
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2023-03-30 11:21:15
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService{

}




