package com.happy.lucky.common.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录成功传输对象
 *
 * @author songyangpeng
 */
@Data
@ApiModel(value = "登录成功返回传输对象")
public class LoginSuccessDto {

    @ApiModelProperty(value = "tokenName")
    private String tokenName;

    @ApiModelProperty(value = "tokenValue")
    private String tokenValue;
}
