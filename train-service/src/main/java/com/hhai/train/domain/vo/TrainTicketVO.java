package com.hhai.train.domain.vo;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 列车信息视图对象（包含余票信息）
 */
@Data
public class TrainTicketVO {
    // 列车ID
    private Long trainId;

    // 列车编号 (如 G1234, K123 等)
    private String trainNumber;

    // 始发站名称
    private String startStation;

    // 始发站ID
    private Long startStationId;

    // 目的站名称
    private String endStation;

    // 目的站ID
    private Long endStationId;

    // 二等座余票数量
    private Integer secondClassSeats;

    // 一等座余票数量
    private Integer firstClassSeats;

    // 商务座/特等座余票数量
    private Integer businessClassSeats;

    // 商务座/特等座余票数量
    private Double price;

    // 无座票余票数量
    private Integer noSeatTickets;

    // 发车时间
    private LocalDateTime departureTime;

    // 到达时间
    private LocalDateTime arrivalTime;

    // 历时 (格式：4小时30分钟)
    private String duration;

    // 列车类型 (高铁/动车/普快等)
    private String trainType;
}