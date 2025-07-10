package com.hhai.train.controller;

import com.hhai.common.utils.Result;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(tags = "列车站点相关接口")
@RestController
@RequestMapping("/trainStation")
@RequiredArgsConstructor
public class TrainStationController {
    private final ITrainStationService trainStationService;
    @ApiOperation("查询车票价格")
    @GetMapping("/trainTicketPrice")
    Result<Double> trainTicketPrice(@RequestParam("stationIds") List<Long> stationIds){
        return trainStationService.queryTrainTicketPrice(stationIds);
    }
}
