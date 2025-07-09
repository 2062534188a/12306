package com.hhai.pay.service.impl;

import com.hhai.pay.domain.po.Payment;
import com.hhai.pay.mapper.PaymentMapper;
import com.hhai.pay.service.IPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhai
 * @since 2025-07-09
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {

}
