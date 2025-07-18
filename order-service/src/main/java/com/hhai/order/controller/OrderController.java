package com.hhai.order.controller;

import com.hhai.common.utils.Result;
import com.hhai.order.domain.dto.OrderFormDTO;
import com.hhai.order.domain.vo.CreatOrderVO;
import com.hhai.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单相关接口")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;
    @ApiOperation("创建订单")
    @PostMapping
    public Result<CreatOrderVO> order(@RequestBody OrderFormDTO orderFormDTO){
        return orderService.createOrder(orderFormDTO);
    }

    @ApiOperation("修改订单状态")
    @PostMapping("OrderSuccess")
    void OrderSuccess(@RequestParam String orderId){
         orderService.markOrderPaySuccess(orderId);
    }
}
