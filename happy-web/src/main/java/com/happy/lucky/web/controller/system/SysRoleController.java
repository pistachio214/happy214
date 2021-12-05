package com.happy.lucky.web.controller.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.domain.SysRoleMenu;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.system.services.ISysRoleMenuService;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestUtils;
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

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public R list(String name) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.orderByDesc("created_at");

        int current = ServletRequestUtils.getIntParameter(req, "cuurent", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 15);

        IPage<SysRole> roles = sysRoleService.page(new Page<>(current, size), queryWrapper);
        return R.success(roles);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:list')")
    public R info(@PathVariable("id") Long id) {
        SysRole sysRole = sysRoleService.getById(id);

        // 获取角色相关联的菜单id
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", id);

        List<SysRoleMenu> roleMenus = sysRoleMenuService.list(queryWrapper);
        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());

        sysRole.setMenuIds(menuIds);
        return R.success(sysRole);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('sys:role:delete')")
    public R delete(@PathVariable("id") Long id) {
        sysRoleService.removeById(id);

        // 删除中间表
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("role_id", id));
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", id));

        // 更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId(id);

        return R.success("");
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:role:save')")
    public R save(@Validated @RequestBody SysRole sysRole) {
        sysRole.setCreatedAt(LocalDateTime.now());
        sysRoleService.save(sysRole);

        return R.success(sysRole);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    public R update(@Validated @RequestBody SysRole sysRole) {
        sysRole.setUpdatedAt(LocalDateTime.now());

        sysRoleService.updateById(sysRole);

        // 更新缓存
        sysUserService.clearUserAuthorityInfoByRoleId(sysRole.getId());

        return R.success(sysRole);
    }

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

        return R.success(menuIds);
    }
}
