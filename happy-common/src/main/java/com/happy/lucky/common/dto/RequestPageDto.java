package com.happy.lucky.common.dto;

import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.BaseUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;

@Setter
@ApiModel
public class RequestPageDto {

    @ApiModelProperty(value = "页码", notes = "默认第1页", dataType = "Integer")
    private Integer current;

    @ApiModelProperty(value = "条数", notes = "默认15条", dataType = "Integer")
    private Integer size;

    public Integer getCurrent() {
        if (BaseUtil.isEmpty(current)) {
            return Const.DEFAULT_CURRENT;
        }
        return current;
    }

    public Integer getSize() {
        if (BaseUtil.isEmpty(size)) {
            return Const.DEFAULT_SIZE;
        }
        return size;
    }
}
