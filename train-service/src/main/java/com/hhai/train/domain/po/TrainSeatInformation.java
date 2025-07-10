package com.hhai.train.domain.po;

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
 * @since 2025-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("train_seat_information")
@ApiModel(value="TrainSeatInformation对象", description="")
public class TrainSeatInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "座位ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分站点ID")
    private Long branchStationId;

    @ApiModelProperty(value = "关联席别ID")
    private Long seatId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "关联车次ID")
    private Long trainId;

    @ApiModelProperty(value = "席别(特等座(商务座) 0/一等座 1/二等座 2/无座3)")
    private Integer seatType;

    @ApiModelProperty(value = "车厢号(商务座1车10个座位/一等座2-3车80个座位) ")
    private Integer carriageNo;

    @ApiModelProperty(value = "排号")
    private Integer rowNo;

    @ApiModelProperty(value = "座位字母")
    private String seatCode;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
