package com.happy.lucky.framework.service;

import com.happy.lucky.common.dto.response.LoginSuccessDto;
import com.happy.lucky.system.dto.RequestAuthAdminLoginDto;

/**
 * 系统验证类
 *
 * @author songyangpeng
 */
public interface ISysAuthService {

    /**
     * 后台管理员登录验证
     *
     * @param dto
     * @return
     */
    LoginSuccessDto doAdminLogin(RequestAuthAdminLoginDto dto);

    /**
     * 退出系统
     */
    void doAdminLogout();

    /**
     * 密码加密
     *
     * @param text
     * @return
     */
    String rsaEncryptByPublic(String text);
}
