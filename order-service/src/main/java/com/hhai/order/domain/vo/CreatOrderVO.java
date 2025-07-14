package com.hhai.order.domain.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreatOrderVO {
    private String payOrderId;

    private String orderId;

    private BigDecimal Amount;

    private LocalDateTime expireTime;

}
