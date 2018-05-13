package com.darkcom.decoration.shiro;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义根据url判断是否有权限访问，在JWTFiler后
 *
 * @author yaojy
 */
public class MyAccessFilter extends AccessControlFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAccessFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        LOGGER.info(this.getPathWithinApplication(request));
        String viewUrl = this.getPathWithinApplication(request);
        LOGGER.info(String.valueOf(this.getSubject(httpServletRequest, response).isAuthenticated()));
        //return getSubject(request, response).isPermitted(viewUrl);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        response401(request, response);

        return false;
    }

    /**
     * 将请求返回到 /401
     */
    private void response401(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.sendRedirect("/401");
    }
}
