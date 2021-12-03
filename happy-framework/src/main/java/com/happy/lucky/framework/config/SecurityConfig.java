package com.happy.lucky.framework.config;

import com.happy.lucky.framework.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    private AuthenticationEntryPointImpl unantuorizedHandler;
//
//    @Autowired
//    private LogoutSuccessHandlerImpl logoutSuccessHandler;
//
//    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    }

    /**
     * 强散列哈希加密实现
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
