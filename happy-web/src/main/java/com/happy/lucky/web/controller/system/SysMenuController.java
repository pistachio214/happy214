package com.happy.lucky.web.controller.system;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.happy.lucky.common.dto.SysMenuDto;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.system.domain.SysMenu;
import com.happy.lucky.system.domain.SysRoleMenu;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.services.ISysMenuService;
import com.happy.lucky.system.services.ISysRoleMenuService;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/sys-menu")
public class SysMenuController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysMenuService sysMenuService;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    /**
     * 用户当前用户的菜单和权限信息
     *
     * @param principal
     * @return
     */
    @GetMapping("/nav")
    public R nav(Principal principal) {
        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        // 获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        // ROLE_admin,ROLE_normal,sys:user:list,....
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserNav();

        return R.success(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map()
        );
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public R list() {
        return R.success(sysMenuService.tree());
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:save')")
    @PostMapping(value = "/save", produces = "application/json;charset=UTF-8")
    public R add(@Validated @RequestBody SysMenu menu) {
        menu.setCreatedAt(LocalDateTime.now());
        sysMenuService.save(menu);
        return R.success(menu);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public R delete(@PathVariable("id") Long id) {
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            return R.error("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        sysMenuService.removeById(id);
        // 同步删除中间关联表
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return R.success();
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAnyAuthority('sys:menu:list')")
    public R info(@PathVariable("id") Long id) {
        return R.success(sysMenuService.getById(id));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:menu:update')")
    public R update(@Validated @RequestBody SysMenu sysMenu) {
        sysMenu.setUpdatedAt(LocalDateTime.now());

        sysMenuService.updateById(sysMenu);

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());

        return R.success(sysMenu);
    }
}
