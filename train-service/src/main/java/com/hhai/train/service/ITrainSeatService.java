package com.hhai.train.service;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface ITrainSeatService {
    Result<HashMap<Integer, Integer>> queryTrainResidueTicket(Long trainId, List<Long> branchStationId);

    Result<HashMap<Integer, Integer>> reservationSeat(List<Long> stationIds, Long trainId, Integer seatType, List<Map<String,Object>> seatCodeOfUserId);
}
