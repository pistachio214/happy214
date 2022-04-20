package com.happy.lucky.system.dto;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Pengsy
 */
@Data
@ApiModel(value = "数据字典列表")
public class RequestDictListDto extends RequestPageDto {

    @ApiModelProperty(value = "字典类型")
    private Integer system;

    @ApiModelProperty(value = "类型名称")
    private String type;
}
