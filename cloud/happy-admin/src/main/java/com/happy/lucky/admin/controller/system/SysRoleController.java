package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.domain.SysRoleMenu;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.system.services.ISysRoleMenuService;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import com.happy.lucky.system.dto.RequestRoleCreateDto;
import com.happy.lucky.system.dto.RequestRoleListDto;
import com.happy.lucky.system.dto.RequestRoleUpdateDto;
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
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @ApiOperation(value = "角色列表", notes = "权限 sys:role:list")
    @GetMapping("/list")
    @SaCheckPermission("sys:role:list")
    public R<IPage<SysRole>> list(RequestRoleListDto dto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(dto.getName()), SysRole::getName, dto.getName());
        queryWrapper.like(StrUtil.isNotBlank(dto.getCode()), SysRole::getCode, dto.getCode());
        queryWrapper.orderByDesc(SysRole::getCreatedAt);

        IPage<SysRole> roles = sysRoleService.page(new Page<>(dto.getCurrent(), dto.getSize()), queryWrapper);
        return R.success(roles);
    }

    @ApiOperation(value = "角色详情", notes = "权限 sys:role:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @GetMapping("/info/{id}")
    @SaCheckPermission("sys:role:list")
    public R<SysRole> info(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);

        // 获取角色相关联的菜单id
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, id);

        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(queryWrapper);
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        sysRole.setMenuIds(menuIds);
        return R.success(sysRole);
    }

    @ApiOperation(value = "删除角色", notes = "权限 sys:role:delete")
    @OperLog(operModul = "角色模块 - 删除角色", operType = Const.DELETE, operDesc = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @DeleteMapping("/delete/{id}")
    @SaCheckPermission("sys:role:delete")
    public R delete(@PathVariable("id") Long id) {
        sysRoleService.removeById(id);

        // 删除中间表
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id));
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));

        // 更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId(id);

        return R.success();
    }

    @ApiOperation(value = "创建角色", notes = "权限 sys:role:save")
    @OperLog(operModul = "角色模块 - 新增角色", operType = Const.SAVE, operDesc = "新增角色")
    @PostMapping("/save")
    @SaCheckPermission("sys:role:save")
    public R<SysRole> save(@Validated @RequestBody RequestRoleCreateDto dto) {
        SysRole sysRole = ConvertUtil.map(dto, SysRole.class);
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRole::getCode, dto.getCode());

        int count = sysRoleService.count(lambdaQueryWrapper);
        if (count > 0) {
            return R.error("唯一编码已存在!");
        }

        sysRoleService.save(sysRole);

        return R.success(sysRole);
    }

    @ApiOperation(value = "编辑角色", notes = "权限 sys:role:update")
    @OperLog(operModul = "角色模块 - 更新角色", operType = Const.EDIT, operDesc = "更新角色")
    @PutMapping("/update")
    @SaCheckPermission("sys:role:update")
    public R<SysRole> update(@Validated @RequestBody RequestRoleUpdateDto dto) {
        SysRole sysRole = ConvertUtil.map(dto, SysRole.class);
        sysRoleService.updateById(sysRole);

        // 更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());

        return R.success(sysRole);
    }

    @ApiOperation(value = "设置角色菜单权限", notes = "权限 sys:role:perm")
    @OperLog(operModul = "角色模块 - 角色设置菜单", operType = Const.EDIT, operDesc = "角色设置菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "path"),
            @ApiImplicitParam(name = "menuIds", value = "菜单权限集合", required = true, dataType = "body")
    })
    @Transactional
    @PostMapping("/perm/{roleId}")
    @SaCheckPermission("sys:role:perm")
    public R perm(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenu.setStatus(1);

            sysRoleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的信息
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        sysRoleMenuService.saveBatch(sysRoleMenus);

        // 删除缓存
        sysUserService.clearUserAuthorityInfoByRoleId(roleId);

        return R.success();
    }
}
