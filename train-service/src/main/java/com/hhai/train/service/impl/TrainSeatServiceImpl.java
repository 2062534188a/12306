package com.hhai.train.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhai.common.utils.Result;
import com.hhai.train.domain.dto.ReservationTicketDTO;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainSeat;
import com.hhai.train.domain.po.TrainSeatInformation;
import com.hhai.train.mapper.TrainMapper;
import com.hhai.train.mapper.TrainSeatMapper;
import com.hhai.train.service.ITrainSeatService;
import com.hhai.train.service.ITrainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainSeatServiceImpl extends ServiceImpl<TrainSeatMapper, TrainSeat> implements ITrainSeatService {

    private final TrainSeatInformationServiceImpl trainSeatInformationService;

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

    @Override
    public Result<HashMap<Integer, Integer>> reservationSeat(ReservationTicketDTO reservationTicketDTO) {
        //扣减对应站点的余票
        // 1. 查询符合条件的ID列表
        List<TrainSeat> trainSeatList = lambdaQuery()
                .in(TrainSeat::getBranchStationId, reservationTicketDTO.getStationIds())
                .eq(TrainSeat::getTrainId, reservationTicketDTO.getTrainId())
                .eq(TrainSeat::getSeatType, reservationTicketDTO.getSeatType())
                .list();// 查询完整实体列表

        List<Long> seatIds = trainSeatList.stream().map(TrainSeat::getId).collect(Collectors.toList());
        // 2. 执行更新（仅当有符合条件的记录时）
        if (!seatIds.isEmpty()) {
            lambdaUpdate()
                    .in(TrainSeat::getId, seatIds) // 直接通过ID更新
                    .setSql("to_next_station_residue_count = to_next_station_residue_count - "+reservationTicketDTO.getSeatCodeOfUserId().size())
                    .update();
        }
        //按照用户要求选择对应座位
        ArrayList<TrainSeatInformation> seatInformationList = new ArrayList<>();
        TrainSeatInformation trainSeatInformation;
        long userId;
        String seatCode;
        Long seatId = trainSeatList.get(0).getId();
        for (ReservationTicketDTO.seatCodeOfUserId userInfo : reservationTicketDTO.getSeatCodeOfUserId()) {
            userId =userInfo.getUserId();
            seatCode = userInfo.getSeatCode();
            int carriageNo = 0;
            int rowNo= 0;
            //计算所属座位的车厢号和座位行号
            TrainSeatInformation trainSeatInfo;
            switch (reservationTicketDTO.getSeatType()){
                case 0:
                    //商务座 : (1号车厢AC F 6行)
                    //车厢为1号车厢
                    carriageNo=1;
                    trainSeatInfo = trainSeatInformationService.lambdaQuery()
                            .eq(TrainSeatInformation::getSeatId, seatId)
                            .eq(TrainSeatInformation::getSeatCode, seatCode)
                            .orderByDesc(TrainSeatInformation::getRowNo)
                            .last("LIMIT 1").one();
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() == 6) {
                        throw new RuntimeException("当前字母无座");
                    }
                    //未有人预定该字母座位
                    if (trainSeatInfo==null){
                        rowNo=1;
                    }
                    //该字母座位有余座
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() < 6) {
                        rowNo=trainSeatInfo.getRowNo()+1;
                    }
                    break;
                case 1:
                    //一等座 : (2-3号车厢AC DF 14行)
                    trainSeatInfo = trainSeatInformationService.lambdaQuery()
                            .eq(TrainSeatInformation::getSeatId, seatId)
                            .eq(TrainSeatInformation::getSeatCode, seatCode)
                            .orderByDesc(TrainSeatInformation::getRowNo)
                            .orderByDesc(TrainSeatInformation::getCarriageNo)
                            .last("LIMIT 1").one();
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() == 14 && trainSeatInfo.getCarriageNo()==3) {
                        throw new RuntimeException("当前字母无座");
                    }
                    //该字母座位有余座(未到最后车厢)
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() == 14 && trainSeatInfo.getCarriageNo()<3){
                        carriageNo=trainSeatInfo.getCarriageNo()+1;
                        rowNo=1;
                    }
                    //未有人预定该字母座位
                    if (trainSeatInfo==null){
                        carriageNo=2;
                        rowNo=1;
                    }
                    //该字母座位有余座
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() < 14) {
                        carriageNo=trainSeatInfo.getCarriageNo();
                        rowNo=trainSeatInfo.getRowNo()+1;
                    }
                    break;
                case 2:
                    //二等座 : (3-16号车厢ABC DF 18行)
                    trainSeatInfo = trainSeatInformationService.lambdaQuery()
                            .eq(TrainSeatInformation::getSeatId, seatId)
                            .eq(TrainSeatInformation::getSeatCode, seatCode)
                            .orderByDesc(TrainSeatInformation::getRowNo)
                            .orderByDesc(TrainSeatInformation::getCarriageNo)
                            .last("LIMIT 1").one();
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() == 18 && trainSeatInfo.getCarriageNo()==16) {
                        throw new RuntimeException("当前字母无座");
                    }
                    //该字母座位有余座(未到最后车厢)
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() == 18 && trainSeatInfo.getCarriageNo()<16){
                        carriageNo=trainSeatInfo.getCarriageNo()+1;
                        rowNo=1;
                    }
                    //未有人预定该字母座位
                    if (trainSeatInfo==null){
                        carriageNo=3;
                        rowNo=1;
                    }
                    //该字母座位有余座()
                    if (trainSeatInfo != null && trainSeatInfo.getRowNo() < 18) {
                        carriageNo=trainSeatInfo.getCarriageNo();
                        rowNo=trainSeatInfo.getRowNo()+1;
                    }
                    break;

            }
            for (TrainSeat trainSeat : trainSeatList) {
                trainSeatInformation = new TrainSeatInformation();
                trainSeatInformation.setUserId(userId);
                //保存坐席字母
                trainSeatInformation.setSeatCode(seatCode);
                //保存坐席id
                trainSeatInformation.setSeatId(trainSeat.getId());
                //保存途径站点
                trainSeatInformation.setBranchStationId(trainSeat.getBranchStationId());
                //保存途径站点
                trainSeatInformation.setOrderId(reservationTicketDTO.getOrderId());
                //保存列车id
                trainSeatInformation.setTrainId(reservationTicketDTO.getTrainId());
                //保存座位等级
                trainSeatInformation.setSeatType(reservationTicketDTO.getSeatType());
                //保存车厢号
                trainSeatInformation.setCarriageNo(carriageNo);
                //保存座位号
                trainSeatInformation.setRowNo(rowNo);

                seatInformationList.add(trainSeatInformation);
            }
            //配置完一个行车人信息就保存一次
            try {
                trainSeatInformationService.saveBatch(seatInformationList);
            } catch (Exception e) {
                throw new RuntimeException("预定座位失败",e);
            }
        }

        return null;
    }

    @Override
    public void cancelSeatReservation(String orderId) {
        //先解除已锁定的座位
        boolean update = trainSeatInformationService.lambdaUpdate().eq(TrainSeatInformation::getOrderId, orderId).set(true, TrainSeatInformation::getSeatStatus, 1).update();
        if (!update) {
            return;
        }
        //查询有几人乘坐
        List<TrainSeatInformation> list = trainSeatInformationService.lambdaQuery().eq(TrainSeatInformation::getOrderId, orderId).list();
        //途径站点信息
        List<Long> seatIds = list.stream()
                .map(TrainSeatInformation::getSeatId)
                .distinct()
                .collect(Collectors.toList());
        //乘车人数
        long seatCount = list.size() - seatIds.size();
        //恢复各个分站点的车票
         lambdaUpdate()
                .in(TrainSeat::getId, seatIds) // 直接通过ID更新
                .setSql("to_next_station_residue_count = to_next_station_residue_count + " + seatCount)
                .update();

    }


}
