package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "更新数据字典")
public class RequestDictEditDto extends RequestDictCreateDto {

    @ApiModelProperty(value = "字典ID", required = true)
    @NotNull(message = "字典ID不能为空")
    private Long id;
}
