package com.hhai.train.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.mapper.TrainMapper;
import com.hhai.train.mapper.TrainSeatMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrainSeatServiceImpl extends ServiceImpl<TrainSeatMapper, TrainSeat> implements ITrainSeatService {

}
