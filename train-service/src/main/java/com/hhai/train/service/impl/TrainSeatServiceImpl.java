package com.hhai.train.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.common.utils.Result;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.mapper.TrainMapper;
import com.hhai.train.mapper.TrainSeatMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class TrainSeatServiceImpl extends ServiceImpl<TrainSeatMapper, TrainSeat> implements ITrainSeatService {

    @Override
    public Result<HashMap<Integer, Integer>> queryTrainResidueTicket(Long trainId, List<Long> branchStationId) {
        List<TrainSeat> lists = lambdaQuery()
                .in(TrainSeat::getBranchStationId, branchStationId)
                .eq(TrainSeat::getTrainId,trainId)
                .list();
        HashMap<Integer, Integer> SeatMap = new HashMap<>();
        for (TrainSeat trainSeat : lists) {
            SeatMap.merge(trainSeat.getSeatType(), trainSeat.getToNextStationResidueCount(), Math::min);
        }

        return Result.success(SeatMap);
    }
}
