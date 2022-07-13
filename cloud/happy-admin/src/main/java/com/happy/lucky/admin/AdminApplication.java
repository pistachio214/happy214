package com.happy.lucky.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 后台管理系统 Api 启动引导
 *
 * @author songyangpeng
 */
@MapperScan(basePackages = {"com.happy.lucky.system.mapper"})
@SpringBootApplication(scanBasePackages = {
        "com.happy.lucky.admin.*",
        "com.happy.lucky.common.*",
        "com.happy.lucky.framework.*",
        "com.happy.lucky.system.*",
})
@ServletComponentScan(basePackages = {"com.happy.lucky.framework.filter.*"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  后台服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
