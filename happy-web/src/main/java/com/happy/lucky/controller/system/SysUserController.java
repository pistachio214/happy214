package com.happy.lucky.controller.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.dto.system.RequestUserCreateDto;
import com.happy.lucky.dto.system.RequestUserListDto;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    private BCryptPasswordEncoder passwordEncoder;

    @ApiOperation(value = "用户数据列表", notes = "操作权限 sys:user:list")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", required = true)
    })
    @Transactional
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R delete(@PathVariable("id") Long id) {
        sysUserService.removeById(id);
        sysUserRoleService.removeById(id);

        return R.success();
    }

    @ApiOperation(value = "新增管理员", notes = "操作权限 sys:user:save")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public R<SysUser> save(@Validated @RequestBody RequestUserCreateDto dto) {
        SysUser sysUser = ConvertUtil.map(dto, SysUser.class);
        sysUser.setCreatedAt(LocalDateTime.now());
        sysUser.setStatus(Const.STATUS_ON);

        // 默认密码
        String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        sysUser.setPassword(password);

        // 默认头像
        sysUser.setAvatar(Const.DEFULT_AVATAR);

        sysUserService.save(sysUser);
        return R.success(sysUser);
    }

    @ApiOperation(value = "重置管理员密码", notes = "操作权限 sys:user:repass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true)
    })
    @PostMapping("/repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public R repass(@RequestBody Long userId) {
        SysUser sysUser = sysUserService.getById(userId);

        sysUser.setPassword(passwordEncoder.encode(Const.DEFULT_PASSWORD));
        sysUser.setUpdatedAt(LocalDateTime.now());

        sysUserService.updateById(sysUser);
        return R.success(Const.DEFULT_PASSWORD);
    }

    @ApiOperation(value = "管理员设置角色", notes = "操作权限 sys:user:role")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "管理员id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "roleIds", value = "角色id集合", required = true, dataType = "Long[]", paramType = "body")
    })
    @Transactional
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public R rolePerm(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {
        List<SysUserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);
            sysUserRole.setCreatedAt(LocalDateTime.now());
            sysUserRole.setStatus(Const.STATUS_ON);

            userRoles.add(sysUserRole);
        });

        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        sysUserRoleService.saveBatch(userRoles);

        // 删除缓存
        SysUser sysUser = sysUserService.getById(userId);
        sysUserService.clearUserAuthorityInfo(sysUser.getUsername());

        return R.success();
    }

}
