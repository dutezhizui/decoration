package com.darkcom.decoration.common;

import com.alibaba.fastjson.JSON;
import com.darkcom.decoration.exception.AuthorizationException;
import com.darkcom.decoration.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义错误处理
 *
 * @author yjy
 */
@Controller
public class MyErrorController extends BasicErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyErrorController.class);
    private static final String EXCEPTION = "exception";

    public MyErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }


    /**
     * 覆盖默认的Json响应
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        LOGGER.debug("error={}", JSON.toJSONString(body));
        //输出自定义的Json格式
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(body.get(EXCEPTION)) && body.get(EXCEPTION).equals(AuthorizationException.class.getName())) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg());
        }
        map.put("code", status);
        map.put("message", body.get("message"));
        return new ResponseEntity<>(map, status);
    }

    /**
     * 覆盖默认的HTML响应
     */
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //请求的状态
        HttpStatus status = getStatus(request);
        response.setStatus(getStatus(request).value());

        Map<String, Object> model = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.TEXT_HTML));
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        //指定自定义的视图
        return (modelAndView == null ? new ModelAndView("error", model) : modelAndView);
    }
}
