package com.happy.lucky.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.happy.lucky.web.*",
        "com.happy.lucky.framework.*",
        "com.happy.lucky.system.*",
        "com.happy.lucky.common.*"})
@MapperScan(basePackages = {"com.happy.lucky.system.mappers"})
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
