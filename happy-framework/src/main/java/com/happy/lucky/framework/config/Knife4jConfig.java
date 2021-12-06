package com.happy.lucky.framework.config;

import com.happy.lucky.common.enums.ResponseStatusEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.*;

@Configuration
//@EnableSwagger2WebMvc
@EnableSwagger2
public class Knife4jConfig {

    //配置content type
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<>(Arrays.asList("application/json", "charset=UTF-8"));


    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        List<Response> globalResponses = new ArrayList<>();
        for (ResponseStatusEnum item : ResponseStatusEnum.values()) {
            globalResponses.add(new ResponseBuilder()
                    .code(item.getCode().toString())
                    .description(item.getMessage())
                    .build());
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .globalResponses(HttpMethod.GET, globalResponses)
                .globalResponses(HttpMethod.POST, globalResponses)
                .globalResponses(HttpMethod.DELETE, globalResponses)
                .globalResponses(HttpMethod.PUT, globalResponses)
                .globalResponses(HttpMethod.PATCH, globalResponses)
                .apiInfo(apiInfoBuilder())
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                //分组名称
                .groupName("2.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.happy.lucky.web.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfoBuilder() {
        Contact contact = new Contact("Songyang Peng", "https://github.com/RogerPeng123", "aileshang0226@163.com");
        return new ApiInfoBuilder()
                .title("开心后台管理系统API文档")
                .description("# 管理系统Api文档")
                .termsOfServiceUrl("http://www.xx.com/")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
