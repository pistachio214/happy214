package com.happy.lucky.controller.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.domain.SysRoleMenu;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.system.services.ISysRoleMenuService;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import com.happy.lucky.dto.system.RequestRoleCreateDto;
import com.happy.lucky.dto.system.RequestRoleListDto;
import com.happy.lucky.dto.system.RequestRoleUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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

    @Resource
    private HttpServletRequest req;

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
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R<IPage<SysRole>> list(RequestRoleListDto dto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(dto.getName()), SysRole::getName, dto.getName());
        queryWrapper.orderByDesc(SysRole::getCreatedAt);

        IPage<SysRole> roles = sysRoleService.page(new Page<>(dto.getCurrent(), dto.getSize()), queryWrapper);
        return R.success(roles);
    }

    @ApiOperation(value = "角色详情", notes = "权限 sys:role:list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    public R<SysRole> info(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);

        // 获取角色相关联的菜单id
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);

        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(queryWrapper);
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        sysRole.setMenuIds(menuIds);
        return R.success(sysRole);
    }

    @ApiOperation(value = "删除角色", notes = "权限 sys:role:delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色id", required = true)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
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
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:save')")
    public R<SysRole> save(@Validated @RequestBody RequestRoleCreateDto dto) {
        SysRole sysRole = ConvertUtil.map(dto, SysRole.class);
        sysRole.setCreatedAt(LocalDateTime.now());
        sysRoleService.save(sysRole);

        return R.success(sysRole);
    }

    @ApiOperation(value = "编辑角色", notes = "权限 sys:role:update")
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public R<SysRole> update(@Validated @RequestBody RequestRoleUpdateDto dto) {
        SysRole sysRole = ConvertUtil.map(dto, SysRole.class);
        sysRole.setUpdatedAt(LocalDateTime.now());
        sysRoleService.updateById(sysRole);

        // 更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());

        return R.success(sysRole);
    }

    @ApiOperation(value = "设置角色菜单权限", notes = "权限 sys:role:perm")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "path"),
            @ApiImplicitParam(name = "menuIds", value = "菜单权限集合", required = true, dataType = "body")
    })
    @Transactional
    @PostMapping("/perm/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public R perm(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds) {
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenu.setCreatedAt(LocalDateTime.now());
            roleMenu.setStatus(1);

            sysRoleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        sysRoleMenuService.saveBatch(sysRoleMenus);

        // 删除缓存
        sysUserService.clearUserAuthorityInfoByRoleId(roleId);

        return R.success();
    }
}
