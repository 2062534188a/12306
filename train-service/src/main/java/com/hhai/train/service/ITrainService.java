package com.hhai.train.service;

import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.vo.TrainTicketVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITrainService  {

    List<TrainTicketVO> queryTrainTickets(TrainTicketDTO trainTicketDTO);
}
