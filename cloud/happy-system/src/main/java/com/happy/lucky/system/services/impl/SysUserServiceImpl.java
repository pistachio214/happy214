package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.RedisUtil;
import com.happy.lucky.system.domain.SysMenu;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.mapper.SysUserMapper;
import com.happy.lucky.system.services.ISysMenuService;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统管理员表 服务实现类
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysMenuService sysMenuService;

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(SysUser::getUsername, username)
                .eq(SysUser::getType, Const.USER_IS_ADMIN);

        return baseMapper.selectOne(userLambdaQueryWrapper);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        SysUser sysUser = baseMapper.selectById(userId);
        String authority = null;
        String key = "GrantedAuthority:" + sysUser.getId();

//        if (redisUtil.hasKey(key)) {
//            // 优先从缓存获取
//            authority = (String) redisUtil.get(key);
//        } else {
            QueryWrapper<SysRole> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.inSql("id", "select `role_id` from `sys_user_role` where `user_id` = " + userId);
            List<SysRole> roles = sysRoleService.list(roleQueryWrapper);
            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {
                List<SysMenu> menus = (List<SysMenu>) sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menuPerms);
            }
            redisUtil.set(key, authority, 60 * 60);

//        }
        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(Long id) {
        String key = "GrantedAuthority:" + id;
        if (redisUtil.hasKey(key)) {
            redisUtil.del(key);
        }
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select `user_id` from `sys_user_role` where `role_id` = " + roleId);
        List<SysUser> sysUsers = baseMapper.selectList(queryWrapper);
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getId());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getId());
        });
    }

    @Override
    public void clearUserRoleInfo(Long id) {
        String roleCacheKey = "GrantedRole:" + id;
        if (redisUtil.hasKey(roleCacheKey)) {
            redisUtil.del(roleCacheKey);
        }
    }
}
