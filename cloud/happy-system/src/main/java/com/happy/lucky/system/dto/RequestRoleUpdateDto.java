package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("编辑角色")
public class RequestRoleUpdateDto extends RequestRoleCreateDto {

    @ApiModelProperty(value = "角色id", required = true)
    private Integer id;

    @ApiModelProperty(value = "菜单id集合", required = true)
    private List<Long> menuIds;
}
