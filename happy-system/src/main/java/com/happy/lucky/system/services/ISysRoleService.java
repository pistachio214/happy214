package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.domain.SysRole;

import java.util.List;

public interface ISysRoleService  extends IService<SysRole> {
    List<SysRole> listRolesByUserId(Long userId);
}
