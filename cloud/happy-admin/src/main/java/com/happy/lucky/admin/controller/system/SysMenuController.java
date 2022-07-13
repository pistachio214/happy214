package com.happy.lucky.admin.controller.system;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.happy.lucky.common.dto.SysMenuDto;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.ConvertUtil;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.framework.annotation.OperLog;
import com.happy.lucky.system.dto.RequestMenuSaveDto;
import com.happy.lucky.system.domain.SysMenu;
import com.happy.lucky.system.domain.SysRoleMenu;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.services.ISysMenuService;
import com.happy.lucky.system.services.ISysRoleMenuService;
import com.happy.lucky.system.services.ISysUserService;
import com.happy.lucky.system.dto.RequestMenuUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Api(tags = "菜单模块")
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
     * @return R
     */
    @SaCheckLogin
    @GetMapping("/nav")
    public R nav() {
        SysUser sysUser = (SysUser) StpUtil.getSession().get("user");
        // 获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        // ROLE_admin,ROLE_normal,sys:user:list,....
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserNav(sysUser.getId());

        //获取用户的昵称和头像
        Map<String, String> user = new HashMap<>();
        user.put("nickname", sysUser.getNickname());
        user.put("avatar", sysUser.getAvatar());

        return R.success(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .put("user", user)
                .map());
    }

    @ApiOperation(value = "菜单列表", notes = "权限 sys:menu:list")
    @OperLog(operModul = "菜单模块 - 菜单列表", operType = Const.LIST, operDesc = "菜单列表")
    @GetMapping("/list")
    @SaCheckPermission("sys:menu:list")
    public R<List<SysMenu>> list() {
        return R.success(sysMenuService.tree());
    }

    @ApiOperation(value = "创建菜单", notes = "权限 sys:menu:save")
    @OperLog(operModul = "菜单模块 - 新增菜单", operType = Const.SAVE, operDesc = "新增菜单")
    @SaCheckPermission("sys:menu:save")
    @PostMapping(value = "/save")
    public R<SysMenu> add(@Validated @RequestBody RequestMenuSaveDto dto) {
        SysMenu menu = ConvertUtil.map(dto, SysMenu.class);
        sysMenuService.save(menu);
        return R.success(menu);
    }

    @ApiOperation(value = "删除菜单", notes = "权限 sys:menu:delete")
    @OperLog(operModul = "菜单模块 - 删除菜单", operType = Const.DELETE, operDesc = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "菜单id", name = "id", required = true)
    })
    @DeleteMapping("/delete/{id}")
    @SaCheckPermission("sys:menu:delete")
    public R delete(@PathVariable("id") Long id) {
        int count = sysMenuService.count(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, id));
        if (count > 0) {
            return R.error("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        sysMenuService.removeById(id);
        // 同步删除中间关联表
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, id));
        return R.success();
    }

    @ApiOperation(value = "菜单详情", notes = "权限 sys:menu:list")
    @OperLog(operModul = "菜单模块 - 菜单详情", operType = Const.INFO, operDesc = "菜单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "菜单id", name = "id", required = true)
    })
    @GetMapping("/info/{id}")
    @SaCheckPermission("sys:menu:list")
    public R<SysMenu> info(@PathVariable("id") Long id) {
        return R.success(sysMenuService.getById(id));
    }

    @ApiOperation(value = "更新菜单", notes = "权限 sys:menu:update")
    @OperLog(operModul = "菜单模块 - 更新菜单", operType = Const.EDIT, operDesc = "更新菜单")
    @PutMapping("/update")
    @SaCheckPermission("sys:menu:update")
    public R<SysMenu> update(@Validated @RequestBody RequestMenuUpdateDto dto) {
        SysMenu sysMenu = ConvertUtil.map(dto, SysMenu.class);

        sysMenuService.updateById(sysMenu);

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());

        return R.success(sysMenu);
    }
}
