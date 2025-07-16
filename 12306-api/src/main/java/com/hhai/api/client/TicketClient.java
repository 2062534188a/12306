package com.hhai.api.client;

import com.hhai.api.dto.CreatPaymentSlipDTO;
import com.hhai.api.dto.PayOrderDTO;
import com.hhai.api.po.Ticket;
import com.hhai.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ticket-service")
public interface TicketClient {

    @PostMapping("/ticket/ticket")
    String ticket(@RequestBody Ticket ticket);

    @PostMapping("/ticket/ticketStatus")
    Boolean ticketStatus(@RequestParam String ticketId);
}
