package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "创建数据字典")
public class RequestDictCreateDto {
    @ApiModelProperty(value = "类型名称", required = true)
    @NotBlank(message = "类型名称不能为空")
    private String type;

    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空")
    private String description;

    @ApiModelProperty(value = "字典类型", required = true)
    @NotNull(message = "字典类型不能为空")
    private Integer system;

    @ApiModelProperty(value = "备注", required = true)
    private String remarks;
}
