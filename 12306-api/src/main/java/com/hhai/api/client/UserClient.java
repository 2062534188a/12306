package com.hhai.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("user-service")
public interface UserClient {
    @PostMapping("userBalance")
    Boolean userBalance(@RequestParam Long userId, @RequestParam BigDecimal balance);
}
