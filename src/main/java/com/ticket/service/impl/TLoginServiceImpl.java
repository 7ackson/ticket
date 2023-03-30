package com.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.entity.TLogin;
import com.ticket.service.TLoginService;
import com.ticket.mapper.TLoginMapper;
import org.springframework.stereotype.Service;

/**
* @author 30669
* @description 针对表【t_login】的数据库操作Service实现
* @createDate 2023-03-30 11:21:15
*/
@Service
public class TLoginServiceImpl extends ServiceImpl<TLoginMapper, TLogin>
    implements TLoginService{

}




