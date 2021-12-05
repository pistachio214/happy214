package com.happy.lucky.system.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.happy.lucky.system.domain.SysUser;

public interface ISysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);
}
