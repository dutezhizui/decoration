package com.darkcom.decoration.common;

/**
 * @author yaojy
 * 返回错误码
 */
public class ResultCode {

    private Integer code;
    private String msg;

    public ResultCode() {

    }

    private ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static final ResultCode OK = new ResultCode(200, "成功");
    public static final ResultCode UNAUTHORIZED = new ResultCode(401, "认证失败");

    public static final ResultCode LOGIN_FAIL = new ResultCode(600001, "登录失败");
    public static final ResultCode INVALID_TOKEN = new ResultCode(600002, "无效的token");
    public static final ResultCode USER_NOT_EXIST = new ResultCode(600003, "获取用户数据错误");
    public static final ResultCode TWICE_PASSWORD_NOT_SAME = new ResultCode(600004, "两次输入的密码不一致");
    public static final ResultCode VERIFY_CODE_ERROR = new ResultCode(600005, "验证码错误");
    public static final ResultCode PASSWORD_ERROR = new ResultCode(600006, "密码错误");
    public static final ResultCode VERIFY_CODE_VALIDATE = new ResultCode(600007, "验证码无效");
    public static final ResultCode PARAMETER_ERROR = new ResultCode(600008, "参数错误");
    public static final ResultCode DATA_NOT_FOUND = new ResultCode(404, "未查询到数据");

}
