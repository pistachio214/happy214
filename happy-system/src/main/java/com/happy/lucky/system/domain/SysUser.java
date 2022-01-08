package com.happy.lucky.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.happy.lucky.common.utils.BaseUtil;
import com.happy.lucky.common.utils.MinioUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@ApiModel
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends Model {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(hidden = true)
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(hidden = true)
    private String city;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updatedAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime lastLogin;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "角色信息")
    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();

    public String getAvatar() {
        if (BaseUtil.isEmpty(avatar)){
            return null;
        }
        return MinioUtil.generatorUrl(avatar);
    }
}
