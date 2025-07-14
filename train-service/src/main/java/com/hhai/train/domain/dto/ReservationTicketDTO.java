package com.hhai.train.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReservationTicketDTO {
    private List<Long> stationIds;
    private Long trainId;
    private String orderId;
    private Integer seatType;
    private List<seatCodeOfUserId> seatCodeOfUserId;
    @Data
    public static class seatCodeOfUserId {
        private String seatCode;
        private Long userId;
    }
}
