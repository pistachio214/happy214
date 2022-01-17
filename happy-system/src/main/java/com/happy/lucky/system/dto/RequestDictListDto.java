package com.happy.lucky.system.dto;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "数据字典列表")
public class RequestDictListDto extends RequestPageDto {
}
