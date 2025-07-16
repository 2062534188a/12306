package com.hhai.order.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.api.client.PayClient;
import com.hhai.api.client.TrainClient;
import com.hhai.api.dto.CreatPaymentSlipDTO;
import com.hhai.api.dto.ReservationTicketDTO;
import com.hhai.common.utils.Result;
import com.hhai.common.utils.UserContext;
import com.hhai.order.constants.MQConstants;
import com.hhai.order.domain.dto.OrderFormDTO;
import com.hhai.order.domain.vo.CreatOrderVO;
import com.hhai.order.mapper.OrderMapper;
import com.hhai.order.service.IOrderService;
import com.hhai.order.domain.po.Order;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final TrainClient trainClient;
    private final PayClient payClient;
    private final RabbitTemplate rabbitTemplate;
    @Override
    @GlobalTransactional
    public Result<CreatOrderVO> createOrder(OrderFormDTO orderFormDTO) {
        //1. 查询是否余票
        Result<HashMap<Integer, Integer>> seatMapResult = trainClient.trainResidueTicket(orderFormDTO.getTrainId(), orderFormDTO.getStationIds());
        if (seatMapResult.getData().get(orderFormDTO.getSeatType())<orderFormDTO.getSeatCodeOfUserId().size()){
            return Result.fail("200","无可用车票");
        }
        //2. 查询车票价格
        Result<Double> doubleResult = trainClient.trainTicketPrice(orderFormDTO.getStationIds());
        Double price = doubleResult.getData();
        //3. 创建订单
        Order order = new Order();

        // 组合成带时间的ID：时间戳 + 分布式ID
        String idWithTime = DateUtil.format(DateUtil.date(), "yyyyMMdd") + "_" + RandomUtil.randomNumbers(8);
        order.setId(idWithTime);
        order.setStatus(0);
        order.setUserId(UserContext.getUser());
        //计算订单金额
        switch (orderFormDTO.getSeatType()){
            case 0: price*=2.5;break;//商务座票价为二等座2.5倍
            case 1: price*=1.2;break;//一等座票价为二等座1.2倍
        }
        order.setAmount(BigDecimal.valueOf(price));
        //支付截止时间15分钟
        order.setExpireTime(LocalDateTime.now().plusMinutes(15));
        //创建订单失败
        // 扣减对应座位余量 并将用户信息载入绑定座位
        ReservationTicketDTO reservationTicketDTO = new ReservationTicketDTO();
        reservationTicketDTO.setStationIds(orderFormDTO.getStationIds());
        reservationTicketDTO.setTrainId(orderFormDTO.getTrainId());
        reservationTicketDTO.setSeatType(orderFormDTO.getSeatType());
        reservationTicketDTO.setSeatCodeOfUserId(orderFormDTO.getSeatCodeOfUserId());
        reservationTicketDTO.setOrderId(order.getId());
        Result<List<String>> reservations = trainClient.reservations(reservationTicketDTO);
        try {
            save(order);
        } catch (Exception e) {
            return Result.fail("500","创建订单失败");
        }

        // 同步创建支付单
        CreatPaymentSlipDTO creatPaymentSlipDTO = new CreatPaymentSlipDTO();
        creatPaymentSlipDTO.setOrderId(order.getId());
        creatPaymentSlipDTO.setAmount(order.getAmount());
        creatPaymentSlipDTO.setExpireTime(order.getExpireTime());
        Result<String> payOrderId = payClient.payOrder(creatPaymentSlipDTO);

        //将生成支付单流水号绑定到订单
        lambdaUpdate().eq(Order::getId,order.getId()).set(true,Order::getPaymentId,payOrderId.getData());


        //创建订单成功 返回订单号
        CreatOrderVO creatOrderVO = new CreatOrderVO();
        creatOrderVO.setAmount(order.getAmount());
        creatOrderVO.setExpireTime(order.getExpireTime());
        creatOrderVO.setOrderId(order.getId());
        creatOrderVO.setPayOrderId(payOrderId.getData());

        // 延迟任务作支付状态变更保底
        rabbitTemplate.convertAndSend(MQConstants.DELAY_EXCHANGE_NAME,
                MQConstants.DELAY_ORDER_KEY,order.getId(), message -> {
            message.getMessageProperties().setDelay(10000);
            return message;
        });

        return Result.success(creatOrderVO);
    }

    @Override
    public void markOrderPaySuccess(String orderId) {
        //修改订单状态为已完成
        lambdaUpdate().eq(Order::getId, orderId).set(true,Order::getStatus,1).update();
    }

    @Override
    public void cancelOrder(String orderId) {
        //修改订单状态为已关闭
        lambdaUpdate().eq(Order::getId, orderId).set(true,Order::getStatus,2).update();
        //锁定已预定的座位
        trainClient.cancelSeatReservation(orderId);
    }
}
