package com.happy.lucky.system.services.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.lucky.common.utils.RedisUtil;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.domain.SysUserRole;
import com.happy.lucky.system.mapper.SysUserRoleMapper;
import com.happy.lucky.system.services.ISysRoleService;
import com.happy.lucky.system.services.ISysUserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 管理员角色链接表 服务实现类
 * </p>
 *
 * @author psy <aileshang0226@163.com>
 * @since 2021-09-30
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public List<String> getRoleList(Long id) {
        String roleCacheKey = "GrantedRole:" + id;
        if (redisUtil.hasKey(roleCacheKey)) {
            String roleList = redisUtil.get(roleCacheKey).toString();
            return Arrays.asList(roleList.split(","));
        }

        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserRole::getUserId, id);

        List<SysUserRole> list = this.list(lambdaQueryWrapper);

        List<String> listRole = new ArrayList<>();
        list.forEach(sysUserRole -> {
            SysRole sysRole = sysRoleService.getById(sysUserRole.getRoleId());
            listRole.add(sysRole.getCode());
        });
        if (listRole.size() > 0) {
            redisUtil.set(roleCacheKey, StringUtils.join(listRole.toArray(), ","));
        }
        return listRole;
    }
}
