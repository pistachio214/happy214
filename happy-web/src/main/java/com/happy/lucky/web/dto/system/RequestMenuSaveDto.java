package com.happy.lucky.web.dto.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "创建菜单")
public class RequestMenuSaveDto {

    @ApiModelProperty(value = "父级菜单id,一级菜单为0", required = true)
    @NotNull(message = "父级菜单不能为空")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称", required = true)
    @NotNull(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotNull(message = "权限编码不能为空")
    private String perms;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单URL")
    private String path;

    @ApiModelProperty(value = "菜单组件")
    private String component;

    @ApiModelProperty(value = "类型 0目录 1菜单 2按钮", required = true)
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "状态 1启用 0禁止", required = true)
    @NotNull(message = "菜单状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "排序号", required = true)
    @NotNull(message = "排序号不能为空")
    private Integer orderNum;
}
