package com.darkcom.decoration.shiro;

import com.darkcom.decoration.exception.AuthorizationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yaojy
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(BasicHttpAuthenticationFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取Authorization字段
        logger.info(String.valueOf(this.getSubject(httpServletRequest, response).isAuthenticated()));
        String authorization = httpServletRequest.getHeader("Authorization");
        try {
            if (authorization != null) {
                JWTToken token = new JWTToken(authorization);
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                getSubject(request, response).login(token);
            } else {
                throw new AuthorizationException();
            }
        } catch (Exception e) {
            throw new AuthorizationException();
        }
        return true;
    }
}
