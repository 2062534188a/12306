package com.hhai.train.controller;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "列车相关接口")
@RestController
@RequestMapping("/trainSeat")
@RequiredArgsConstructor
public class TrainSeatController {
    private final ITrainSeatService trainSeatService;

    @GetMapping("queryTrainResidueTicket")
    public Result<HashMap<Integer, Integer>> queryTrainResidueTicket(@RequestParam("trainId") Long trainId, @RequestParam("branchStationId") List<Long> branchStationId){
        return trainSeatService.queryTrainResidueTicket(trainId,branchStationId);
    }
}
