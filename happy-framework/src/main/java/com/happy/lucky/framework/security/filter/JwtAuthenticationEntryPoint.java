package com.happy.lucky.framework.security.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.info("认证失败！未登录！");
        httpServletResponse.setStatus(ResponseStatusEnum.SUCCESS.getCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        httpServletResponse.getWriter().print(R.error(e.getMessage()));

        String res = JSON.toJSONString(R.error(ResponseStatusEnum.NOT_LOGIN.getCode(), ResponseStatusEnum.NOT_LOGIN.getMessage()));
        httpServletResponse.getWriter().print(Arrays.toString(res.getBytes(StandardCharsets.UTF_8)));
    }
}
