package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "异常日志列表")
public class RequestExceptionLogListDto extends RequestOperLogListDto {
}
