package com.happy.lucky.system.services.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.mappers.SysRoleMapper;
import com.happy.lucky.system.services.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select `role_id` from `sys_user_role` where `user_id` = " + userId);

        return this.list(queryWrapper);
    }
}
