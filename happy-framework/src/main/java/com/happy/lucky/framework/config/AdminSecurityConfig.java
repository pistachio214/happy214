package com.happy.lucky.framework.config;

import com.happy.lucky.common.utils.BaseUtil;
import com.happy.lucky.framework.security.filter.CaptchaFilter;
import com.happy.lucky.framework.security.filter.JWTAuthenticationFilter;
import com.happy.lucky.framework.security.filter.JwtAuthenticationEntryPoint;
import com.happy.lucky.framework.security.handle.ILoginFailureHandler;
import com.happy.lucky.framework.security.handle.ILoginSuccessHandler;
import com.happy.lucky.framework.security.handle.ILogoutSuccessHandler;
import com.happy.lucky.framework.service.IDaoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Objects;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ILoginFailureHandler loginFailureHandler;

    @Autowired
    private ILoginSuccessHandler loginSuccessHandler;

    @Autowired
    private ILogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CaptchaFilter captchaFilter;

    @Autowired
    private IDaoAuthenticationProvider iDaoAuthenticationProvider;

    @Value("${happy.security.matchers}")
    private String whiteList;

    @Value("${happy.security.admin-login}")
    private String loginUrl;

    @Value("${happy.security.admin-logout}")
    private String logoutUrl;

    /**
     * 格式化过滤路由配置
     *
     * @return
     */
    private String[] getUrlWhitelist() {
        if (!BaseUtil.isEmpty(whiteList)) {
            return whiteList.trim().split(",");
        }
        return null;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(authenticationManager());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(iDaoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()

                .logout()
                .logoutUrl(logoutUrl)
                .logoutSuccessHandler(logoutSuccessHandler)

                // 登录配置
                .and()
                .formLogin()
                .loginPage(loginUrl)
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)

                // 白名单
                .and()
                .authorizeRequests()
                .antMatchers(Objects.requireNonNull(getUrlWhitelist()))
                .permitAll()
                .anyRequest()
                .authenticated()

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                // 登录验证码校验过滤器
                .and()
                .addFilter(jwtAuthenticationFilter())
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
