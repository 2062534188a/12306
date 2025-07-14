package com.hhai.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreatPaymentSlipDTO {
    private String orderId;
    private LocalDateTime expireTime;
    private BigDecimal amount;
}
