package com.hhai.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.order.mapper.OrderMapper;
import com.hhai.order.service.IOrderService;
import com.hhai.order.domain.po.Order;

public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
