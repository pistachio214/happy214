package com.happy.lucky.web.dto.system;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "角色列表")
@Data
public class RequestRoleListDto extends RequestPageDto {

    @ApiModelProperty(value = "角色名称")
    private String name;
}