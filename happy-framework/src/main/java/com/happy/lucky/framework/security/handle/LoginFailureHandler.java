package com.happy.lucky.framework.security.handle;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(ResponseStatusEnum.SUCCESS.getCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String res = JSON.toJSONString(R.error(e.getMessage()));
        httpServletResponse.getWriter().print(Arrays.toString(res.getBytes(StandardCharsets.UTF_8)));
    }
}
