package com.hhai.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhai.train.domain.po.Train;
import com.hhai.train.domain.vo.TrainTicketVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface TrainMapper extends BaseMapper<Train> {

}
