package com.happy.lucky.web.service;

import com.happy.lucky.common.utils.BaseUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenService {

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;

    @Value("${token.prefix}")
    private String tokenPrefix;

    @Value("${token.loginUserKey}")
    private String loginUserKey;

    @Value("${token.loginTokenKey}")
    private String loginTokenKey;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final long MILLS_MINUTE_TEN = 20 * 60 * 1000L;

    public Object getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (!BaseUtil.isEmpty(token)) {
            try {
                Claims claims = parseToken(token);

                String uuid = (String) claims.get(loginUserKey);
                String userKey = getTokenKey(uuid);

                //TODO 从redis中获取user数据
            } catch (Exception e) {

            }
        }

        return null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);

        if (!BaseUtil.isEmpty(token) && token.startsWith(tokenPrefix)) {
            token = token.replace(tokenPrefix, "");
        }
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String getTokenKey(String uuid) {
        return loginTokenKey + uuid;
    }

}
