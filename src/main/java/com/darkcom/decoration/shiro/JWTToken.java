package com.darkcom.decoration.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author yaojy
 */
public class JWTToken implements AuthenticationToken {

    /**
     * token
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
