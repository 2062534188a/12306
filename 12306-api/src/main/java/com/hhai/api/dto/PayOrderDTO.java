package com.hhai.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayOrderDTO {
    @ApiModelProperty("(0支付中/1成功/2失败)")
    private Integer status;
}
