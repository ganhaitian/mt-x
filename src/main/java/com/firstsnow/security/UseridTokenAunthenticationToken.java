package com.firstsnow.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authentication的实现，用于和平台对接后，uid以及token的校验
 * Created by ganhaitian on 2016/3/4.
 */
public class UseridTokenAunthenticationToken extends AbstractAuthenticationToken{

    private final String userId;
    private String token;
    private final int platformId;

    public UseridTokenAunthenticationToken(Object userId, Object token,int platformId) {
        super(null);
        this.userId = userId.toString();
        this.token = token.toString();
        this.platformId = platformId;
    }

    public UseridTokenAunthenticationToken(Object userId, Object token,int platformId,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId.toString();
        this.token = token.toString();
        this.platformId = platformId;
        super.setAuthenticated(true);
    }

    public Object getCredentials() {
        return token;
    }

    public Object getPrincipal() {
        return userId;
    }

    public int getPlatformId(){
        return this.platformId;
    }
}
