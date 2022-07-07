package com.happy.lucky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理系统 Api 启动引导
 *
 * @author songyangpeng
 */
@MapperScan(basePackages = {"com.happy.lucky.system.mapper"})
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  后台服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
