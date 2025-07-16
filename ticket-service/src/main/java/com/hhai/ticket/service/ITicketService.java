package com.hhai.ticket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhai.ticket.domain.po.Ticket;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hhai
 * @since 2025-07-06
 */
public interface ITicketService extends IService<Ticket> {
    String createTicket(Ticket ticket);

    Boolean modifyTicketStatus(String ticketId);
}
