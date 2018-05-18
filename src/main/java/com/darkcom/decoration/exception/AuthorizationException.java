package com.darkcom.decoration.exception;

/**
 * 认证授权异常
 *
 * @author yjy
 */
public class AuthorizationException extends BusinessException {
    public AuthorizationException() {

    }

    public AuthorizationException(Integer code, String message) {
        super(code, message);
    }
}
