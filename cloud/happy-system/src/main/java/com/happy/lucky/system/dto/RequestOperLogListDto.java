package com.happy.lucky.system.dto;

import com.happy.lucky.common.dto.RequestPageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author songyangpeng
 */
@Data
@ApiModel(value = "日志列表")
public class RequestOperLogListDto extends RequestPageDto {

    @ApiModelProperty(value = "操作员名称")
    private String operUserName;

    @ApiModelProperty(value = "开始操作时间")
    private String startAt;

    @ApiModelProperty(value = "结束操作时间")
    private String endAt;

}
