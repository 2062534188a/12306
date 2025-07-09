package com.hhai.train.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.common.utils.Result;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.domain.po.TrainStation;
import com.hhai.train.mapper.TrainSeatMapper;
import com.hhai.train.mapper.TrainStationMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TrainStationService extends ServiceImpl<TrainStationMapper, TrainStation> implements ITrainStationService {

    @Override
    public Result<Double> queryTrainTicketPrice(List<Long> stationIds) {
        //取出分站点id对应的站点信息
        List<TrainStation> trainStations = lambdaQuery().in(TrainStation::getId, stationIds).list();
        trainStations.remove(trainStations.size()-1);
        //累计区间站点价格
        double price = 0.0;
        for (TrainStation trainStation : trainStations) {
            price+=Double.parseDouble(trainStation.getToNextStationPrice().toString());
        }
        return Result.success(price);
    }
}
