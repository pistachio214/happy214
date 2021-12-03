package com.happy.lucky.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import com.happy.lucky.common.utils.StringUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        Integer code = ResponseStatusEnum.UNANTHORIZED.getCode();

        String msg = StringUtil.format("请求访问：{},认证失败,无法访问系统资源", httpServletRequest.getRequestURI());

        renderStr(httpServletResponse, JSON.toJSONString(R.error(code, msg)));
    }

    private String renderStr(HttpServletResponse response, String str) {
        try {
            response.setStatus(ResponseStatusEnum.SUCCESS.getCode());
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().print(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
