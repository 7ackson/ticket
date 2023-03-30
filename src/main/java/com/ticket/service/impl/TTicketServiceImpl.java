package com.ticket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ticket.entity.TTicket;
import com.ticket.service.TTicketService;
import com.ticket.mapper.TTicketMapper;
import org.springframework.stereotype.Service;

/**
* @author 30669
* @description 针对表【t_ticket】的数据库操作Service实现
* @createDate 2023-03-30 11:21:15
*/
@Service
public class TTicketServiceImpl extends ServiceImpl<TTicketMapper, TTicket>
    implements TTicketService{

}




