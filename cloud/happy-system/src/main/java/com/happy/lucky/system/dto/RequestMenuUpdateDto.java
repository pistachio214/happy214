package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "更新菜单")
public class RequestMenuUpdateDto extends RequestMenuSaveDto {
    @ApiModelProperty(value = "菜单id", required = true)
    private Integer id;
}
