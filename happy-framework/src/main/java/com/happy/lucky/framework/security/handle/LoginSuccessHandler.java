package com.happy.lucky.framework.security.handle;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.happy.lucky.common.enums.ResponseStatusEnum;
import com.happy.lucky.common.utils.JwtUtil;
import com.happy.lucky.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String jwt = jwtUtil.generateToken(authentication.getName());

        Map<String, String> data = new HashMap<>();
        data.put(jwtUtil.getHeader(), jwt);

        httpServletResponse.setStatus(ResponseStatusEnum.SUCCESS.getCode());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        String res = JSON.toJSONString(R.success(data));
        httpServletResponse.getWriter().print(Arrays.toString(res.getBytes(StandardCharsets.UTF_8)));
    }


}
