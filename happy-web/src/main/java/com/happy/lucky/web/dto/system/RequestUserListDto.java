package com.happy.lucky.web.dto.system;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RequestUserListDto extends RequestPageDto {
    @ApiModelProperty(value = "账号")
    private String username;
}
