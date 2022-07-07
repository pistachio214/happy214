package com.happy.lucky.system.dto;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "角色列表")
@Data
public class RequestRoleListDto extends RequestPageDto {

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "唯一编码")
    private String code;
}
