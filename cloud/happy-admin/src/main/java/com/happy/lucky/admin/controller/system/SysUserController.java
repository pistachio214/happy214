package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.system.dto.RequestUserCreateDto;
import com.happy.lucky.system.dto.RequestUserListDto;
import com.happy.lucky.system.dto.RequestUserSaveAvatarDto;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.framework.service.ISysAuthService;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统管理员表 前端控制器
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Api(tags = "管理员模块")
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysAuthService sysAuthService;

    @ApiOperation(value = "管理员数据列表", notes = "操作权限 sys:user:list")
    @OperLog(operModul = "管理员模块 - 管理员列表", operType = Const.LIST, operDesc = "管理员列表")
    @GetMapping("/list")
    @SaCheckPermission("sys:user:list")
    public R<IPage<SysUser>> list(RequestUserListDto dto) {

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StrUtil.isNotBlank(dto.getUsername()), SysUser::getUsername, dto.getUsername())
                .orderByDesc(SysUser::getCreatedAt);

        IPage<SysUser> pageData = sysUserService.page(new Page<>(dto.getCurrent(), dto.getSize()), lambdaQueryWrapper);

        pageData.getRecords().forEach(u -> {
            u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
        });

        return R.success(pageData);
    }

    @ApiOperation(value = "删除管理员", notes = "操作权限 sys:user:delete")
    @OperLog(operModul = "管理员模块 - 删除管理员", operType = Const.DELETE, operDesc = "删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", required = true)
    })
    @Transactional
    @DeleteMapping("/delete/{id}")
    @SaCheckPermission("sys:user:delete")
    public R delete(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        sysUserRoleService.removeById(id);

        return R.success();
    }

    @ApiOperation(value = "新增管理员", notes = "操作权限 sys:user:save")
    @OperLog(operModul = "管理员模块 - 新增管理员", operType = Const.SAVE, operDesc = "新增管理员")
    @PostMapping("/save")
    @SaCheckPermission("sys:user:save")
    public R<SysUser> save(@Validated @RequestBody RequestUserCreateDto dto) {
        SysUser sysUser = ConvertUtil.map(dto, SysUser.class);

        // 默认密码
        String password = sysAuthService.rsaEncryptByPublic(Const.DEFULT_PASSWORD);
        sysUser.setPassword(password);

        // 默认头像
        sysUser.setAvatar(Const.DEFULT_AVATAR);

        sysUserService.save(sysUser);
        return R.success(sysUser);
    }

    @ApiOperation(value = "重置管理员密码", notes = "操作权限 sys:user:repass")
    @OperLog(operModul = "管理员模块 - 重置管理员密码", operType = Const.EDIT, operDesc = "重置管理员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true)
    })
    @PostMapping("/repass")
    @SaCheckPermission("sys:user:repass")
    public R<String> repass(@RequestBody Long userId) {
        SysUser sysUser = sysUserService.getById(userId);

        sysUser.setPassword(sysAuthService.rsaEncryptByPublic(Const.DEFULT_PASSWORD));

        sysUserService.updateById(sysUser);
        return R.success(Const.DEFULT_PASSWORD);
    }

    @ApiOperation(value = "管理员设置角色", notes = "操作权限 sys:user:role")
    @OperLog(operModul = "管理员模块 - 管理员设置角色", operType = Const.EDIT, operDesc = "管理员设置角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "roleIds", value = "角色id集合", required = true, dataType = "Long[]", paramType = "body")
    })
    @Transactional
    @PostMapping("/role/{userId}")
    @SaCheckPermission("sys:user:role")
    public R rolePerm(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {
        List<SysUserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);
            sysUserRole.setStatus(Const.STATUS_ON);

            userRoles.add(sysUserRole);
        });

        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        sysUserRoleService.saveBatch(userRoles);

        // 删除缓存
        SysUser sysUser = sysUserService.getById(userId);
        sysUserService.clearUserAuthorityInfo(sysUser.getId());

        sysUserService.clearUserRoleInfo(userId);

        return R.success();
    }

    @ApiOperation(value = "修改管理员头像", notes = "操作权限 sys:user:save:avatar")
    @OperLog(operModul = "管理员模块 - 修改管理员头像", operType = Const.EDIT, operDesc = "修改管理员头像")
    @PostMapping("/saveAvatar")
    @SaCheckPermission("sys:user:save:avatar")
    public R saveAvatar(@Validated @RequestBody RequestUserSaveAvatarDto dto) {
        SysUser sysUser = (SysUser) StpUtil.getSession().get("user");
        LambdaUpdateWrapper<SysUser> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper
                .eq(SysUser::getId, sysUser.getId())
                .set(SysUser::getAvatar, dto.getAvatar());

        if (!sysUserService.update(lambdaUpdateWrapper)) {
            return R.error("修改头像失败");
        }

        return R.success();
    }

}
