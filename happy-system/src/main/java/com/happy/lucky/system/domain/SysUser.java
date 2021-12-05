package com.happy.lucky.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends Model {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", notes = "数据ID", dataType = "Long", hidden = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(required = true, value = "用户名称", dataType = "String")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(hidden = true)
    private String password;

    @ApiModelProperty(hidden = true)
    private String avatar;

    @ApiModelProperty(value = "邮箱", dataType = "String")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(hidden = true)
    private String city;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updatedAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime lastLogin;

    @ApiModelProperty(hidden = true)
    private Integer status;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();
}
