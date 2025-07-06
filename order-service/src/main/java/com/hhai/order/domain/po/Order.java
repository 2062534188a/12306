package com.hhai.order.domain.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号(业务生成)")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "车次ID")
    private Long trainId;

    @ApiModelProperty(value = "出发站ID")
    private Long fromStationId;

    @ApiModelProperty(value = "到达站ID")
    private Long toStationId;

    @ApiModelProperty(value = "席别")
    private String seatType;

    @ApiModelProperty(value = "购票数量")
    private Integer seatCount;

    @ApiModelProperty(value = "总金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "状态(0待支付/1已支付/2已取消)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "支付截止时间")
    private LocalDateTime expireTime;


}
