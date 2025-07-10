package com.hhai.pay.controller;


import com.hhai.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hhai
 * @since 2025-07-09
 */
@Api(tags = "支付相关接口")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @ApiOperation("生成支付单")
    @PostMapping
    public Result<String> payOrder(@RequestParam String orderId,@RequestParam LocalDateTime expireTime){
//        return orderService.createOrder(orderFormDTO);
        return null;
    }
}
