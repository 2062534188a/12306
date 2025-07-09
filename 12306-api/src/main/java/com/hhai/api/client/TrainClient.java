package com.hhai.api.client;

import com.hhai.common.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@FeignClient("train-service")
public interface TrainClient {
    @GetMapping("/trainSeat/queryTrainResidueTicket")
    Result<HashMap<Integer, Integer>> queryTrainResidueTicket(@RequestParam("trainId") Long trainId, @RequestParam("branchStationId") List<Long> branchStationId);

    @GetMapping("/trainStation/queryTrainTicketPrice")
    Result<Double> queryTrainTicketPrice(@RequestParam("stationIds") List<Long> stationIds);
}
