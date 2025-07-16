package com.hhai.train.controller;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.ReservationTicketDTO;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "列车相关接口")
@RestController
@RequestMapping("/trainSeat")
@RequiredArgsConstructor
public class TrainSeatController {
    private final ITrainSeatService trainSeatService;
    @ApiOperation("查询列车余票")
    @GetMapping("trainResidueTicket")
    public Result<HashMap<Integer, Integer>> trainResidueTicket(@RequestParam("trainId") Long trainId, @RequestParam("branchStationId") List<Long> branchStationId){
        return trainSeatService.queryTrainResidueTicket(trainId,branchStationId);
    }
    @ApiOperation("预定座位")
    @PostMapping("reservations")
    public Result<List<String>> reservations(@RequestBody ReservationTicketDTO reservationTicketDTO){
        return trainSeatService.reservationSeat(reservationTicketDTO);
    }

    @PostMapping("cancelSeatReservation")
    void cancelSeatReservation(@RequestParam String orderId){
        trainSeatService.cancelSeatReservation(orderId);
    }
}
