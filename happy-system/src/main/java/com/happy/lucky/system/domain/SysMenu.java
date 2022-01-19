package com.happy.lucky.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel
public class SysMenu extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @ApiModelProperty(value = "父级菜单编号")
    private Long parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单URL
     */
    @ApiModelProperty(value = "菜单URL")
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @ApiModelProperty(value = "菜单授权")
    private String perms;

    @ApiModelProperty(value = "菜单组件")
    private String component;

    /**
     * 类型 0：目录 1：菜单 2：按钮
     */
    @ApiModelProperty(value = "菜单类型 0目录 1菜单 2按钮")
    private Integer type;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "菜单排序")
    @TableField("orderNum")
    private Integer orderNum;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "状态 1启用 0禁止")
    private Integer status;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

    @ApiModelProperty(value = "下级菜单列表")
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

}
