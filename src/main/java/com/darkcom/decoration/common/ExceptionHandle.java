package com.darkcom.decoration.common;

import com.darkcom.decoration.exception.BusinessException;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author yaojy
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result Handle(Exception e) {
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return Result.fail(businessException.getCode(), businessException.getMessage());
        }
        if (e instanceof ShiroException) {
            return Result.fail(Result.UNAUTHORIZED, e.getMessage());
        } else {
            MissingServletRequestParameterException methodArgumentNotValidException=(MissingServletRequestParameterException )e;
            //List<ObjectError> errors = methodArgumentNotValidException.getMessage().getBindingResult().getAllErrors();
            String tips = methodArgumentNotValidException.getMessage();

            //将系统异常以打印出来
            logger.info("[系统异常]{}", e);
            return Result.fail(Result.BAD_REQUEST, "未知错误");
        }

    }

}
