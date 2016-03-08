package com.firstsnow.security;

import org.apache.commons.lang.math.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接入多平台的用户校验
 * Created by ganhaitian on 2016/3/4.
 */
public class MultPlatformAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String DEFAULT_USERID_KEY = "userId";
    public static final String DEFAULT_PASSWORD_KEY = "token";
    public static final String DEFAULT_PLATFORM_KEY = "platformId";

    private String userIdParameter = DEFAULT_USERID_KEY;
    private String tokenParameter = DEFAULT_PASSWORD_KEY;
    private String platformIdParameter = DEFAULT_PLATFORM_KEY;

    protected MultPlatformAuthenticationFilter() {
        super("/j_spring_security_check");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String userId = request.getParameter(userIdParameter);
        String token = request.getParameter(tokenParameter);
        String platformIdStr = request.getParameter(platformIdParameter);

        if(userId == null)
            userId = "";

        if(token == null)
            token = "";

        int platformId = 0;
        if(NumberUtils.isNumber(platformIdStr)){
            platformId = Integer.parseInt(request.getParameter(platformIdParameter));
        }

        UseridTokenAunthenticationToken authRequest = new UseridTokenAunthenticationToken(userId,token,platformId);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
