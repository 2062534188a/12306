package com.hhai.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReservationTicketDTO {
    private List<Long> stationIds;
    private Long trainId;
    private Integer seatType;
    private String orderId ;
    private List<seatCodeOfUserId> seatCodeOfUserId;

    @Data
    public static class seatCodeOfUserId {
        private String seatCode;
        private Long userId;
    }
}
