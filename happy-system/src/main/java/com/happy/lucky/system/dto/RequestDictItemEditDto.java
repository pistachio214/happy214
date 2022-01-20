package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "编辑数据字典项")
public class RequestDictItemEditDto extends RequestDictItemCreateDto {

    @ApiModelProperty(value = "id", required = true)
    private Long id;
}
