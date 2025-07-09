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
import java.time.LocalTime;

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
@TableName("train_station")
@ApiModel(value="TrainStation对象", description="")
public class TrainStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分站点ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "主站点ID")
    private Long stationId;

    @ApiModelProperty(value = "关联车次ID")
    private Long trainId;

    @ApiModelProperty(value = "站点名称")
    private String stationName;

    @ApiModelProperty(value = "到达时间")
    private LocalDateTime arrivalTime;

    @ApiModelProperty(value = "发车时间")
    private LocalDateTime departureTime;

    @ApiModelProperty(value = "站点顺序")
    private Integer stationOrder;

    @ApiModelProperty(value = "到下一站点区间价格")
    private BigDecimal toNextStationPrice;

    @ApiModelProperty(value = "起始站点 0/终点站 1")
    private Integer stationState;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
