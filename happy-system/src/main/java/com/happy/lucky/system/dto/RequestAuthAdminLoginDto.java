package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author songyangpeng
 */
@Data
@ApiModel(value = "后台管理员登录请求对象")
public class RequestAuthAdminLoginDto {

    @NotNull(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号", required = true)
    private String username;

    @NotNull(message = "登录密码不能为空")
    @ApiModelProperty(value = "登录密码", required = true)
    private String password;

    @NotEmpty(message = "登录密码不能为空")
    @ApiModelProperty(value = "是否记住登录", required = true)
    private boolean remember;

    @NotNull(message = "验证码code不能为空")
    @ApiModelProperty(value = "验证码code", required = true)
    private String code;

    @NotNull(message = "验证码token不能为空")
    @ApiModelProperty(value = "验证码token", required = true)
    private String token;

}
