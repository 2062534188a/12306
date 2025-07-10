package com.hhai.train.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.TrainTicketDTO;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.domain.vo.TrainTicketVO;
import com.hhai.train.mapper.TrainMapper;
import com.hhai.train.mapper.TrainStationMapper;
import com.hhai.train.service.ITrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train> implements ITrainService {

    private final TrainStationMapper trainStationMapper;

    private final TrainSeatServiceImpl trainSeatService;

    @Override
    public Result<List<TrainTicketVO>> queryTrainTickets(LocalDate departureDate, Long endStationId, Long startStationId, Integer trainType) {

        //1. 查询对应列车信息
        List<Map<String, Object>> trainsByStations = trainStationMapper.findTrainsByStations(startStationId, endStationId,departureDate,departureDate.plusDays(1));
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
            if (lock==1||startStationId==Long.parseLong(trainsByStation.get("station_id").toString())){
                //检查到起点 开始存入list中
                list.add(trainsByStation);
                //打开存储开关
                lock=1;
                if (endStationId==Long.parseLong(trainsByStation.get("station_id").toString())){
                    //关闭存储开关
                    lock=0;
                    continue;
                }
            }
            //如果未检出起点 直接到终点
            if (endStationId==Long.parseLong(trainsByStation.get("station_id").toString())){
                //记录错误列车id
                errorTrainId = (Long) trainsByStation.get("id");
            }
        }
        ArrayList<Long> stationIds = new ArrayList<>();
        for (Map<String, Object> trainsByStation : list) {
            stationIds.add(Long.parseLong(trainsByStation.get("branch_station_id").toString()));
            //当前为出发站点
            if ( startStationId== Long.parseLong(trainsByStation.get("station_id").toString())) {
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
                trainTicketVO.setStartStationId(startStationId);

                //2.4 起始站名称
                trainTicketVO.setStartStation(trainsByStation.get("station_name").toString());

                //2.9 发车时间
                trainTicketVO.setDepartureTime(LocalDateTime.parse(
                        trainsByStation.get("departure_time").toString().split("\\.")[0],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));


                //2.10 列车类型 trainType
                trainTicketVO.setTrainType(String.valueOf(trainType));
                continue;
            }
            //为目的站点
            if (endStationId==Long.parseLong(trainsByStation.get("station_id").toString())) {
                //2.11 目的站id
                trainTicketVO.setEndStationId(endStationId);
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

                //保存途径分站点
                trainTicketVO.setStationIds(new ArrayList<>(stationIds));
                //2.15 将余票计算出来并保存
                List<TrainSeat> lists = trainSeatService.lambdaQuery().in(TrainSeat::getBranchStationId, stationIds).eq(TrainSeat::getTrainId,Long.parseLong(trainsByStation.get("id").toString())).list();

                Map<Integer, Integer> SeatMap = new HashMap<>();
                for (TrainSeat trainSeat : lists) {
                    SeatMap.merge(trainSeat.getSeatType(), trainSeat.getToNextStationResidueCount(), Math::min);
                }
                trainTicketVO.setBusinessClassSeats(SeatMap.get(0));
                trainTicketVO.setFirstClassSeats(SeatMap.get(1));
                trainTicketVO.setSecondClassSeats(SeatMap.get(2));
                trainTicketVO.setNoSeatTickets(SeatMap.get(3));
                if (price!=0){
                    //保存车票价格
                    trainTicketVO.setPrice(price);
                    //清空车站id
                    stationIds.clear();
                    //保存已配置的列车信息
                    trainTicketVOList.add(trainTicketVO);
                    continue;
                }
            }
            //2.16累加价格
            price+=Double.parseDouble(trainsByStation.get("to_next_station_price").toString());
        }

        return Result.success(trainTicketVOList);
    }




}
