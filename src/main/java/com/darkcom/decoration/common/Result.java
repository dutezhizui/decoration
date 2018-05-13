package com.darkcom.decoration.common;


import java.io.Serializable;

/**
 * @param <T>
 * @author yaojy
 */
public class Result<T> implements Serializable {
    public static final Integer OK = 200;
    public static final Integer ACCEPT = 202;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer FORBIDDEN = 403;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public Result() {
        this(OK);
    }

    public Result(Integer code) {
        this(code, (String) null);
    }

    public Result(Integer code, String message) {
        this(code, message, null);
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T payload) {
        this(OK, (String) null, payload);
    }

    public static <T> Result<T> succeed() {
        return new Result();
    }

    public static <T> Result<T> succeed(T payload) {
        return new Result(payload);
    }

    public static <T> Result<T> fail(Integer code) {
        return new Result(code);
    }

    public static <T> Result<T> fail(Integer code, String message) {
        return new Result(code, message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{code=" + this.code + ", message='" + this.message + '\'' + ", data=" + this.data + '}';
    }
}
