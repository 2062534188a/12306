package com.hhai.train.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel(description = "查询站点车票实体")
public class TrainTicketDTO {
    // 出发日期
    private LocalDate departureDate;

    // 始发站点ID
    private Long startStationId;

    // 目的站点ID
    private Long endStationId;

    // 车次类型枚举（0-高铁/动车，1-普通火车）
    private Integer trainType;
}
