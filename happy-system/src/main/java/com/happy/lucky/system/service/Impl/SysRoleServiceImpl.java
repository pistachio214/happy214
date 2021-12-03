package com.happy.lucky.system.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.happy.lucky.system.domain.SysRole;
import com.happy.lucky.system.mapper.SysRoleMapper;
import com.happy.lucky.system.service.ISysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
}
