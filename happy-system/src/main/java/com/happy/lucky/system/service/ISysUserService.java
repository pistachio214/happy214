package com.happy.lucky.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.domain.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);
}
