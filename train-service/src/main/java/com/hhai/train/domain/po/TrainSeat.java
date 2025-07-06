package com.hhai.train.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2025-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("train_seat")
@ApiModel(value="TrainSeat对象", description="")
public class TrainSeat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "席别ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联车次ID")
    private Long trainId;

    @ApiModelProperty(value = "分站点ID")
    private Long branch_station_id;

    @ApiModelProperty(value = "席别(商务座 0/一等座 1/二等座 2/无座 3)")
    private Integer seatType;

    @ApiModelProperty(value = "总座位数")
    private Integer totalCount;

    @ApiModelProperty(value = "到下一站点区间余座数")
    private Integer to_next_station_residue_count;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
