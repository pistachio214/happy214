package com.happy.lucky.framework.handler;

import cn.dev33.satoken.stp.StpInterface;
import com.happy.lucky.system.services.ISysUserRoleService;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义全县验证接口扩展
 *
 * @author songyangpeng
 */
@Component
public class SaPermissionHandler implements StpInterface {

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        String authority = sysUserService.getUserAuthorityInfo(Long.valueOf(o.toString()));
        return Arrays.asList(authority.split(","));
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return sysUserRoleService.getRoleList(Long.valueOf(o.toString()));
    }
}
