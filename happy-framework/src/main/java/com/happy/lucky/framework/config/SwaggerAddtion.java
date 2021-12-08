package com.happy.lucky.framework.config;

import com.fasterxml.classmate.TypeResolver;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.*;

@Component
public class SwaggerAddtion implements ApiListingScannerPlugin {

    @Value("${happy.security.admin-login}")
    private String loginUrl;

    @Value("${happy.security.admin-logout}")
    private String logoutUrl;

    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        return Arrays.asList(loginApiDescription(), logoutApiDescription());
    }

    /**
     * 退出登录接口
     *
     * @return
     */
    private ApiDescription logoutApiDescription() {
        Operation logoutOperation =
                new OperationBuilder(cachingOperationNameGenerator())
                        .method(HttpMethod.DELETE)
                        .summary("退出系统")
                        .notes("退出后台系统")
                        .consumes(mediaTypes(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .produces(mediaTypes(MediaType.APPLICATION_JSON_VALUE))
                        .tags(mediaTypes("后台验证模块"))
                        .responseMessages(responseMessages())
                        .build();

        return new ApiDescription(
                "logout", logoutUrl, "后台退出接口",
                Arrays.asList(logoutOperation), false);
    }

    /**
     * 登录接口
     *
     * @return
     */
    private ApiDescription loginApiDescription() {
        Operation usernamePasswordOperation =
                new OperationBuilder(cachingOperationNameGenerator())
                        .method(HttpMethod.POST)
                        .summary("用户名密码登录")
                        .notes("username / password 登录")
                        .consumes(mediaTypes(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .produces(mediaTypes(MediaType.APPLICATION_JSON_VALUE))
                        .tags(mediaTypes("后台验证模块"))
                        .parameters(parameters())
                        .responseMessages(responseMessages())
                        .build();

        return new ApiDescription(
                "login", loginUrl, "后台登陆接口",
                Arrays.asList(usernamePasswordOperation), false);
    }

    private Set<ResponseMessage> responseMessages() {
        ResponseMessage responseMessage = new ResponseMessageBuilder()
                .code(ResponseStatusEnum.SUCCESS.getCode())
                .message(ResponseStatusEnum.SUCCESS.getMessage())
                .build();

        Set<ResponseMessage> responseMessageSet = new HashSet<ResponseMessage>();
        responseMessageSet.add(responseMessage);

        return responseMessageSet;
    }

    private Set<String> mediaTypes(String type) {
        Set<String> types = new HashSet<String>();
        types.add(type);

        return types;
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    private List<Parameter> parameters() {
        Parameter username = new ParameterBuilder()
                .description("账号")
                .name("username")
                .type(new TypeResolver().resolve(String.class))
                .parameterType("query")
                .required(true)
                .modelRef(new ModelRef("string"))
                .build();
        Parameter password = new ParameterBuilder()
                .description("密码")
                .name("password")
                .type(new TypeResolver().resolve(String.class))
                .parameterType("query")
                .required(true)
                .modelRef(new ModelRef("string"))
                .build();
        Parameter token = new ParameterBuilder()
                .description("token")
                .name("token")
                .type(new TypeResolver().resolve(String.class))
                .parameterType("query")
                .required(true)
                .modelRef(new ModelRef("string"))
                .build();
        Parameter code = new ParameterBuilder()
                .description("code")
                .name("code")
                .type(new TypeResolver().resolve(String.class))
                .parameterType("query")
                .required(true)
                .modelRef(new ModelRef("string"))
                .build();

        return Arrays.asList(username, password, token, code);
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }

    private CachingOperationNameGenerator cachingOperationNameGenerator() {
        return new CachingOperationNameGenerator();
    }
}
