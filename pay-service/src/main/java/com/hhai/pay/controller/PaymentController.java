package com.hhai.pay.controller;


import com.hhai.api.dto.CreatPaymentSlipDTO;
import com.hhai.api.dto.PayOrderDTO;
import com.hhai.common.utils.Result;
import com.hhai.pay.service.IPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final IPaymentService paymentService;

    @ApiOperation("生成支付单")
    @PostMapping("payOrder")
    public Result<String> payOrder(@RequestBody CreatPaymentSlipDTO creatPaymentSlipDTO){
        return paymentService.createPaymentSlip(creatPaymentSlipDTO);
    }
    @ApiOperation("根据订单号查询支付单")
    @GetMapping("PayOrderByBizOrderNo")
    Result<PayOrderDTO> PayOrderByBizOrderNo(@RequestParam String orderId){
        return paymentService.queryPayOrderByBizOrderNo(orderId);
    }

    @ApiOperation("支付订单")
    @PostMapping("payOrderByBalance")
    public Result<String> payOrderByBalance(@RequestParam String payOrderNo){
        return paymentService.payOrderByBalance(payOrderNo);
    }
}
