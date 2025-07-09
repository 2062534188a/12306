package com.hhai.order.service;

import com.hhai.common.utils.Result;
import com.hhai.order.domain.dto.OrderFormDTO;

public interface IOrderService {
    Result<String> createOrder(OrderFormDTO orderFormDTO);
}
