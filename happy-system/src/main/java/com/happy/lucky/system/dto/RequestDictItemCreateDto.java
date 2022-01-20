package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "新增数据字典项")
public class RequestDictItemCreateDto {
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "字典id", required = true)
    @NotNull(message = "字典id不能为空")
    private Long dictId;

    @ApiModelProperty(value = "数据值", required = true)
    @NotBlank(message = "数据值不能为空")
    private String value;

    @ApiModelProperty(value = "标签名", required = true)
    @NotBlank(message = "标签名不能为空")
    private String label;

    @ApiModelProperty(value = "描述", required = true)
    @NotBlank(message = "描述不能为空")
    private String description;

    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remarks;


}
