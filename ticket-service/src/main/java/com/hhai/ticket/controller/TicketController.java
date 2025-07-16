package com.hhai.ticket.controller;


import com.hhai.common.utils.Result;
import com.hhai.ticket.domain.po.Ticket;
import com.hhai.ticket.service.ITicketService;
import com.hhai.ticket.service.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hhai
 * @since 2025-07-06
 */
@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping("ticket")
    public String ticket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }
    @PostMapping("/ticket/ticketStatus")
    public Boolean ticketStatus(@RequestParam String ticketId){
        return ticketService.modifyTicketStatus(ticketId);
    }
}
