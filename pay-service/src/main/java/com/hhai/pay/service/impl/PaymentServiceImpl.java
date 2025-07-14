package com.hhai.pay.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.db.sql.Order;
import com.hhai.api.client.OrderClient;
import com.hhai.api.client.UserClient;
import com.hhai.api.dto.CreatPaymentSlipDTO;
import com.hhai.api.dto.PayOrderDTO;
import com.hhai.common.utils.Result;
import com.hhai.common.utils.UserContext;
import com.hhai.pay.domain.po.Payment;
import com.hhai.pay.mapper.PaymentMapper;
import com.hhai.pay.service.IPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Queue;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hhai
 * @since 2025-07-09
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements IPaymentService {

    private final UserClient userClient;

    private final OrderClient orderClient;

    @Override
    public Result<String> createPaymentSlip(CreatPaymentSlipDTO creatPaymentSlipDTO) {
        Payment payment = new Payment();
        // 生成分布式ID
        long distId = IdUtil.getSnowflake().nextId();
        // 组合成带时间的ID：时间戳 + 分布式ID
        String idWithTime = DateUtil.format(DateUtil.date(), "yyyyMMdd") +  distId;
        payment.setId(idWithTime);
        payment.setOrderId(creatPaymentSlipDTO.getOrderId());
        payment.setAmount(creatPaymentSlipDTO.getAmount());
        payment.setExpireTime(creatPaymentSlipDTO.getExpireTime());
        payment.setStatus(0);
        payment.setPayType(0);
        try {
            save(payment);
        } catch (Exception e) {
            throw new RuntimeException("生成支付单失败",e);
        }


        return Result.success(payment.getId());
    }

    @Override
    public Result<PayOrderDTO> queryPayOrderByBizOrderNo(String orderId) {
        Payment payment = lambdaQuery().eq(Payment::getOrderId, orderId).one();
        PayOrderDTO payOrderDTO = new PayOrderDTO();
        payOrderDTO.setStatus(payment.getStatus());
        return Result.success(payOrderDTO);
    }

    @Override
    public Result<String> payOrderByBalance(String payOrderNo) {
        Payment payment = getById(payOrderNo);
        if (payment==null||payment.getStatus()==1){
            return Result.fail("orderFail","请勿重复支付订单");
        }
        if (payment.getStatus()==2){
            return Result.fail("orderFail","订单已失效");
        }
        Long userId = UserContext.getUser();

        if (userClient.userBalance(userId,payment.getAmount())) {
            //修改订单状态
            orderClient.OrderSuccess(payment.getOrderId());

            return Result.success("支付成功");
        }else {
            return Result.fail("deductBalanceFail","余额不足");
        }

    }
}
