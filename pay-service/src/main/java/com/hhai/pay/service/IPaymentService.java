package com.hhai.pay.service;

import com.hhai.api.dto.CreatPaymentSlipDTO;
import com.hhai.api.dto.PayOrderDTO;
import com.hhai.common.utils.Result;
import com.hhai.pay.domain.po.Payment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hhai
 * @since 2025-07-09
 */
public interface IPaymentService extends IService<Payment> {

    Result<String> createPaymentSlip( CreatPaymentSlipDTO creatPaymentSlipDTO);

    Result<PayOrderDTO> queryPayOrderByBizOrderNo(String orderId);

    Result<String> payOrderByBalance(String payOrderNo);

}
