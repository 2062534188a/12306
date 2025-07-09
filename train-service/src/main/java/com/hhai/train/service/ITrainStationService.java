package com.hhai.train.service;


import com.hhai.common.utils.Result;

import java.util.List;

public interface ITrainStationService {

    Result<Double> queryTrainTicketPrice(List<Long> stationIds);

}
