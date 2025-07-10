package com.hhai.order.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.api.client.TrainClient;
import com.hhai.common.utils.Result;
import com.hhai.common.utils.UserContext;
import com.hhai.order.domain.dto.OrderFormDTO;
import com.hhai.order.mapper.OrderMapper;
import com.hhai.order.service.IOrderService;
import com.hhai.order.domain.po.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final TrainClient trainClient;
    private final OrderMapper orderMapper;
    @Override
    public Result<String> createOrder(OrderFormDTO orderFormDTO) {
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
        // 生成分布式ID（假设是雪花ID）
        long distId = IdUtil.getSnowflake().nextId();

        // 组合成带时间的ID：时间戳 + 分布式ID
        String idWithTime = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss") + "_" + distId;
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
        try {
            save(order);
        } catch (Exception e) {
            return Result.fail("500","创建订单失败");
        }
        //5. TODO 延迟任务作支付状态变更保底
        // TODO 同步创建支付单
        // TODO 扣减对应座位余量 并将用户信息载入绑定座位
//        trainClient.reservations(orderFormDTO.getStationIds(),orderFormDTO.getTrainId(),orderFormDTO.getSeatType(),orderFormDTO.getSeatCodeOfUserId());
        //创建订单成功 返回订单号
        return Result.success(order.getId());
    }
}
