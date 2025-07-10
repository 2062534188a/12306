package com.hhai.order.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "创建订单实体")
public class OrderFormDTO {
    //列车id
    private Long trainId;

    //出发时间
//    private LocalDateTime departureTime;

    // 到达时间
//    private LocalDateTime arrivalTime;

    //始发站点
//    private Long startStationId;

    //目的站点
//    private Long endStationId;

    //用户类型
    private Long userType;

    //座位类型
    private Integer seatType;

    //用户id和对应座位字母
    private List<Map<String,Object>> seatCodeOfUserId;

    //途径站点id
    private List<Long> stationIds;


}
