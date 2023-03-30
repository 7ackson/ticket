package com.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.entity.TOrder;
import com.ticket.service.TOrderService;
import com.ticket.mapper.TOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 30669
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2023-03-30 11:21:15
*/
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder>
    implements TOrderService{

}




