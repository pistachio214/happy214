package com.happy.lucky.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ILogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setStatus(ResponseStatusEnum.SUCCESS.getCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        httpServletResponse.getWriter().print(JSON.toJSONString(R.success()));
    }
}
