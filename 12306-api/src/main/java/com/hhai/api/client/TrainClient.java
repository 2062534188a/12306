package com.hhai.api.client;

import com.hhai.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeignClient("train-service")
public interface TrainClient {

    @GetMapping("/trainStation/trainTicketPrice")
    Result<Double> trainTicketPrice(@RequestParam("stationIds") List<Long> stationIds);

    @GetMapping("/trainSeat/trainResidueTicket")
    Result<HashMap<Integer, Integer>> trainResidueTicket(@RequestParam("trainId") Long trainId, @RequestParam("branchStationId") List<Long> branchStationId);

    @PostMapping("/trainSeat/reservations")
     Result<HashMap<Integer, Integer>> reservations(@RequestParam List<Long> stationIds,@RequestParam Long trainId,@RequestParam Integer seatType,@RequestParam List<Map<String,Object>> seatCodeOfUserId);
}
