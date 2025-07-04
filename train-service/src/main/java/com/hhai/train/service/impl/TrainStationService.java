package com.hhai.train.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.domain.po.TrainStation;
import com.hhai.train.mapper.TrainSeatMapper;
import com.hhai.train.mapper.TrainStationMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrainStationService extends ServiceImpl<TrainStationMapper, TrainStation> implements ITrainStationService {

}
