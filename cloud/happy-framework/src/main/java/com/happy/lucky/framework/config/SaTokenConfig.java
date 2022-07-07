package com.happy.lucky.framework.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * sa-token配置信息
 *
 * @author songyangpeng
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("启动成功: Sa-Token配置如下: " + SaManager.getConfig());
        //注册注解拦截器
        registry.addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**");

        //注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/sys-auth/admin/doLogin", "/webjars/**", "/favicon.ico",
                        "/sys-auth/captcha", "/doc.html**", "/swagger-resources",
                        "/common/**", "/v2/api-docs**"
                );
    }
}
