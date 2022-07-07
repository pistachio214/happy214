package com.happy.lucky.framework.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.happy.lucky.common.dto.response.LoginSuccessDto;
import com.happy.lucky.common.enums.LoginDeviceEnum;
import com.happy.lucky.common.lang.Const;
import com.happy.lucky.common.utils.RedisUtil;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.dto.RequestAuthAdminLoginDto;
import com.happy.lucky.framework.service.ISysAuthService;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 实现验证类
 *
 * @author songyangpeng
 */
@Service
public class SysAuthServiceImpl implements ISysAuthService {

    @Value("${sa-token.private-key}")
    private String privateKey;

    @Value("${sa-token.public-key}")
    private String publicKey;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public LoginSuccessDto doAdminLogin(RequestAuthAdminLoginDto dto) {
        if (!dto.getCode().equals(redisUtil.hget(Const.CAPTCHA_KEY, dto.getToken()).toString())) {
            throw new RuntimeException("验证码错误");
        }

        // 一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY, dto.getToken());

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, dto.getUsername());

        SysUser sysUser = sysUserService.getOne(lambdaQueryWrapper);
        if (sysUser == null) {
            throw new RuntimeException("没有该账号信息");
        }

        if (!SaSecureUtil.rsaDecryptByPrivate(privateKey, sysUser.getPassword()).equals(dto.getPassword())) {
            throw new RuntimeException("密码信息不正确");
        }

        if (dto.isRemember()) {
            StpUtil.login(sysUser.getId(), new SaLoginModel()
                    //此次登录的客户端标识,用于[同端互斥登录]时指定此次登录的设备名称
                    .setDevice(LoginDeviceEnum.COMPUTER_TERMINAL.getDevice())
                    //是否持久Cookie(临时cookie在浏览器关闭时会自动删除,持久cookie在重新打开后依然存在)
                    .setIsLastingCookie(true)
            );
        } else {
            StpUtil.login(sysUser.getId(), false);
        }

        StpUtil.getSession().set("user", sysUser);

        //获取到token给到标准返回
        LoginSuccessDto loginSuccessDto = new LoginSuccessDto();
        loginSuccessDto.setTokenName(StpUtil.getTokenInfo().getTokenName());
        loginSuccessDto.setTokenValue(StpUtil.getTokenInfo().getTokenValue());

        return loginSuccessDto;
    }

    @Override
    public void doAdminLogout() {
        StpUtil.getSession().delete("user");
        StpUtil.logout();
    }

    @Override
    public String rsaEncryptByPublic(String text) {
        return SaSecureUtil.rsaEncryptByPublic(publicKey, text);
    }
}
