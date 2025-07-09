package com.hhai.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.po.TrainStation;
import com.hhai.train.domain.vo.TrainTicketVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface TrainStationMapper extends BaseMapper<TrainStation> {
    // 在 TrainMapper.xml 中定义：
    @Select("SELECT t.id,t.train_number,ts.id as branch_station_id,ts.station_state,ts.departure_time,ts.arrival_time,ts.station_id,ts.to_next_station_price,ts.station_order,ts.station_name FROM train t" +
            " JOIN train_station ts ON t.id = ts.train_id" +
            " WHERE t.id IN (SELECT train_id FROM train_station WHERE station_id = #{startId} INTERSECT SELECT train_id FROM train_station WHERE station_id = #{endId})" +
            "AND ts.departure_time > #{departureDate} AND ts.departure_time < #{endDate} "
            )
    List<Map<String, Object>>  findTrainsByStations(@Param("startId") Long startId, @Param("endId") Long endId, @Param("departureDate") LocalDate departureDate, @Param("endDate") LocalDate endDate);
}
