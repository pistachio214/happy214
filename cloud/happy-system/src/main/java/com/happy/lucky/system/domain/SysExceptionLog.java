package com.happy.lucky.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 异常日志表
 * </p>
 *
 * @author Roger Peng
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysExceptionLog对象", description = "异常日志表")
public class SysExceptionLog extends Model {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "请求方式")
    private String operRequMethod;

    @ApiModelProperty(value = "请求参数")
    private String excRequParam;

    @ApiModelProperty(value = "异常名称")
    private String excName;

    @ApiModelProperty(value = "异常信息")
    private String excMessage;

    @ApiModelProperty(value = "操作者")
    private Long operUserId;

    @ApiModelProperty(value = "操作员名称")
    private String operUserName;

    @ApiModelProperty(value = "操作方法")
    private String operMethod;

    @ApiModelProperty(value = "请求URL")
    private String operUri;

    @ApiModelProperty(value = "请求IP")
    private String operIp;

    @ApiModelProperty(value = "操作版号")
    private String operVer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;


}
