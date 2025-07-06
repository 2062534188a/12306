package com.hhai.order.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单相关接口")
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    @ApiOperation("创建订单")
    @PostMapping
    public Long createOrder(@RequestBody OrderFormDTO orderFormDTO){
        return orderService.createOrder(orderFormDTO);
    }
}
