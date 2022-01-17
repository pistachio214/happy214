package com.happy.lucky.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "修改头像")
public class RequestUserSaveAvatarDto {

    @ApiModelProperty(value = "头像")
    private String avatar;
}
