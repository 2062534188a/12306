package com.hhai.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhai.common.utils.Result;
import com.hhai.order.domain.dto.OrderFormDTO;
import com.hhai.order.domain.po.Order;
import com.hhai.order.domain.vo.CreatOrderVO;

public interface IOrderService extends IService<Order> {
    Result<CreatOrderVO> createOrder(OrderFormDTO orderFormDTO);

    void markOrderPaySuccess(String orderId);

    void cancelOrder(String orderId);
}
