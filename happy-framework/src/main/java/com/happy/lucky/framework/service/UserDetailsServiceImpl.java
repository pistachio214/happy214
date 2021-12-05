package com.happy.lucky.framework.service;

import com.happy.lucky.framework.domain.AccountUser;
import com.happy.lucky.system.domain.SysUser;
import com.happy.lucky.system.services.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(s);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码不正确!");
        }

        if (sysUser.getStatus() != 1) {
            throw new UsernameNotFoundException("用户状态不可用!");
        }
        return new AccountUser(sysUser.getId(), sysUser.getUsername(),
                sysUser.getPassword(), getUserAuthority(sysUser.getId()));
    }

    //获取权限数据
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        // 通过内置的工具类，把权限字符串封装成GrantedAuthority列表
        return AuthorityUtils.commaSeparatedStringToAuthorityList(sysUserService.getUserAuthorityInfo(userId));
    }
}
