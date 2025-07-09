package com.hhai.train.controller;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.service.ITrainService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "列车相关接口")
@RestController
@RequestMapping("/train")
@RequiredArgsConstructor
public class TrainController {
    private final ITrainService trainService;

    @PostMapping("getTrains")
    public Result<List<TrainTicketVO>> getTrains(@RequestBody TrainTicketDTO trainTicketDTO) {
        return trainService.queryTrainTickets(trainTicketDTO);
    }


}
