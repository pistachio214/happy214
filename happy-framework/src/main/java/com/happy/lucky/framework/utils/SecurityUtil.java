package com.happy.lucky.framework.utils;

import com.happy.lucky.framework.domain.AccountUser;
import com.happy.lucky.system.domain.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 安全工具
 */
@Component
public class SecurityUtil {

    /**
     * 获取当前用户的信息
     *
     * @return AccountUser
     */
    public static SysUser getCurrentUser() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details instanceof SysUser ? (SysUser) details : null;
    }
}
