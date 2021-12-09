package com.happy.lucky.framework.utils;

import com.happy.lucky.framework.domain.AccountUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具
 */
public class SecurityUtil {

    /**
     * 获取当前用户的信息
     *
     * @return AccountUser
     */
    public static AccountUser getCurrentUser() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return details instanceof AccountUser ? (AccountUser) details : null;
    }
}
