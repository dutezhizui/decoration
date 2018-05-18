package com.darkcom.decoration.dto.request;

import javax.validation.constraints.NotNull;

/**
 * 忘记密码
 * @author yjy
 */
public class ForgetPasswordRequest {
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String verifyCode;
    @NotNull
    private String phone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
