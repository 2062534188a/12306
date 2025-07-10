package com.hhai.train.controller;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.service.ITrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Api(tags = "列车相关接口")
@RestController
@RequestMapping("/train")
@RequiredArgsConstructor
public class TrainController {
    private final ITrainService trainService;
    @ApiOperation("查询站点车票")
    @GetMapping("trains")
    public Result<List<TrainTicketVO>> trains(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
                                                 @RequestParam Long endStationId,
                                                 @RequestParam Long startStationId,
                                                 @RequestParam Integer trainType) {
        return trainService.queryTrainTickets(departureDate,endStationId,startStationId,trainType);
    }


}
