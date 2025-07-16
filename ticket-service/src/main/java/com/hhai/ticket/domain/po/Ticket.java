package com.hhai.ticket.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author hhai
 * @since 2025-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ticket")
@ApiModel(value="Ticket对象", description="")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "票据ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "关联订单号")
    private String orderId;

    @ApiModelProperty(value = "乘车人ID")
    private Long passengerId;

    @ApiModelProperty(value = "车次ID")
    private Long trainId;

    @ApiModelProperty(value = "席别(特等座(商务座) 0/一等座 1/二等座 2/无座3)")
    private Integer seatType;

    @ApiModelProperty(value = "车厢号(商务座1车10个座位/一等座2-3车80个座位) ")
    private Integer carriageNo;

    @ApiModelProperty(value = "排号")
    private Integer rowNo;

    @ApiModelProperty(value = "座位字母")
    private String seatCode;

    @ApiModelProperty(value = "出发站点id")
    private Long startStationId;

    @ApiModelProperty(value = "出发站点名称")
    private String startStation;

    @ApiModelProperty(value = "目标站点id")
    private Long endStationId;

    @ApiModelProperty(value = "目标站点名称")
    private String endStation;

    @ApiModelProperty(value = "发车时间")
    private LocalDateTime departureTime;

    @ApiModelProperty(value = "到达时间")
    private LocalDateTime arrivalTime;

    @ApiModelProperty(value = "票据状态(0未使用/1已使用/3锁定状态)")
    private Integer ticketStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
