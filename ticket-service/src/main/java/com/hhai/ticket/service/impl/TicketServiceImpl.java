package com.hhai.ticket.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.hhai.ticket.domain.po.Ticket;
import com.hhai.ticket.mapper.TicketMapper;
import com.hhai.ticket.service.ITicketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhai
 * @since 2025-07-06
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements ITicketService {

    @GlobalTransactional
    @Override
    public String createTicket(Ticket ticket) {

        String ticketId = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS")+ RandomUtil.randomNumbers(4);
        ticket.setId(ticketId);
        save(ticket);
        return ticketId;
    }
    @GlobalTransactional
    @Override
    public Boolean modifyTicketStatus(String ticketId) {
        return lambdaUpdate().eq(Ticket::getId, ticketId).update();
    }
}
