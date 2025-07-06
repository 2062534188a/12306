package com.hhai.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.domain.po.TrainStation;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.mapper.TrainMapper;
import com.hhai.train.mapper.TrainStationMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {

    private final TrainStationMapper trainStationMapper;

    private final TrainSeatServiceImpl trainSeatService;

    @Override
    public List<TrainTicketVO> queryTrainTickets(TrainTicketDTO trainTicketDTO) {
        //1. 查询对应列车信息
        List<Map<String, Object>> trainsByStations = trainStationMapper.findTrainsByStations(trainTicketDTO.getStartStationId(), trainTicketDTO.getEndStationId(),trainTicketDTO.getDepartureDate(),trainTicketDTO.getDepartureDate().plusDays(1));
        //2. 筛选出两个站点之间的信息
        TrainTicketVO trainTicketVO=new TrainTicketVO();

        double price=0;
        List<TrainTicketVO> trainTicketVOList = new ArrayList<TrainTicketVO>();
        //分离出所需要的站点信息
        int lock=0;
        long errorTrainId = 0;
        List<Map<String, Object>> list=new ArrayList<>();
        for (Map<String, Object> trainsByStation : trainsByStations) {
            //检查当前列车是否错误数据
            if (errorTrainId==(Long) trainsByStation.get("id")){
                continue;
            }
            if (lock==1||trainTicketDTO.getStartStationId()==Long.parseLong(trainsByStation.get("station_id").toString())){
                //检查到起点 开始存入list中
                list.add(trainsByStation);
                //打开存储开关
                lock=1;
                if (trainTicketDTO.getEndStationId()==Long.parseLong(trainsByStation.get("station_id").toString())){
                    //关闭存储开关
                    lock=0;
                    continue;
                }
            }
            //如果未检出起点 直接到终点
            if (trainTicketDTO.getEndStationId()==Long.parseLong(trainsByStation.get("station_id").toString())){
                //记录错误列车id
                errorTrainId = (Long) trainsByStation.get("id");
            }
        }
        System.out.println(list);

        for (Map<String, Object> trainsByStation : list) {
            //当前为出发站点
            long stationId = Long.parseLong(trainsByStation.get("station_id").toString());

            if ( trainTicketDTO.getStartStationId()== stationId) {
                //初始化配置
                trainTicketVO=new TrainTicketVO();
                price=0;
                //累加价格
                price+=Double.parseDouble(trainsByStation.get("to_next_station_price").toString());

                //2.1 列车id
                trainTicketVO.setTrainId((Long) trainsByStation.get("id"));
                //2.2 列车号
                trainTicketVO.setTrainNumber(trainsByStation.get("train_number").toString());

                //2.3 起始站id
                trainTicketVO.setStartStationId(trainTicketDTO.getStartStationId());

                //2.4 起始站名称
                trainTicketVO.setStartStation(trainsByStation.get("station_name").toString());

                //2.8 TODO 座位余量
                List<TrainSeat> lists = trainSeatService.lambdaQuery().eq(TrainSeat::getBranch_station_id, stationId).list();
                for (TrainSeat trainSeat : lists) {
                    System.out.println(trainSeat+"===========");
                }

//                for (TrainSeat trainSeat : lists) {
//                    switch (trainSeat.getSeatType()){
//                        case 0:
//                            trainTicketVO.setBusinessClassSeats(trainSeat.getResidueCount());
//                            break;
//                        case 1:trainTicketVO.setFirstClassSeats(trainSeat.getResidueCount());
//                            break;
//                        case 2:trainTicketVO.setSecondClassSeats(trainSeat.getResidueCount());
//                            break;
//                        case 3:trainTicketVO.setNoSeatTickets(trainSeat.getResidueCount());
//                            break;
//                    }
//                }

                //2.9 发车时间
                trainTicketVO.setDepartureTime(LocalDateTime.parse(
                        trainsByStation.get("departure_time").toString().split("\\.")[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));


                //2.10 列车类型 trainType
                trainTicketVO.setTrainType(String.valueOf(trainTicketDTO.getTrainType()));
                continue;
            }
            //为目的站点
            if (trainTicketDTO.getEndStationId()==Long.parseLong(trainsByStation.get("station_id").toString())) {
                //2.11 目的站id
                trainTicketVO.setEndStationId(trainTicketDTO.getEndStationId());
                //2.12 目的站名称
                trainTicketVO.setEndStation(trainsByStation.get("station_name").toString());
                //2.13 到达时间
                trainTicketVO.setArrivalTime(LocalDateTime.parse(
                        trainsByStation.get("arrival_time").toString().split("\\.")[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
                //2.14 历时
                Duration duration = Duration.between(trainTicketVO.getDepartureTime(),trainTicketVO.getArrivalTime());
                trainTicketVO.setDuration(duration.toHours()+"小时"+duration.toMinutes() % 60+"分钟");

                if (price!=0){
                    //保存车票价格
                    trainTicketVO.setPrice(price);
                    //保存已配置的列车信息
                    trainTicketVOList.add(trainTicketVO);
                    continue;
                }
            }
            //2.16累加价格
            price+=Double.parseDouble(trainsByStation.get("to_next_station_price").toString());
        }

        return trainTicketVOList;
    }
}
