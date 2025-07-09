package com.hhai.train.service;

import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;


public interface ITrainService  {

    Result<List<TrainTicketVO>> queryTrainTickets(TrainTicketDTO trainTicketDTO);


}
