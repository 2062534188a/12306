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
 * @since 2025-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ticket")
@ApiModel(value="Ticket对象", description="")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "票据ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "关联订单号")
    private String orderId;

    @ApiModelProperty(value = "车次ID")
    private Long trainId;

    @ApiModelProperty(value = "车厢号")
    private Integer carriageNumber;

    @ApiModelProperty(value = "座位号(5A)")
    private String seatNumber;

    @ApiModelProperty(value = "乘车人ID")
    private Long passengerId;

    @ApiModelProperty(value = "票据状态(0未使用/1已使用)")
    private Integer ticketStatus;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
