package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.common.ResultCode;
import com.darkcom.decoration.dto.request.UserRegisterRequest;
import com.darkcom.decoration.exception.BusinessException;
import com.darkcom.decoration.model.SmsRecord;
import com.darkcom.decoration.model.User;
import com.darkcom.decoration.service.ISmsSenderService;
import com.darkcom.decoration.service.IUserService;
import com.darkcom.decoration.utils.JWTUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yaojy
 */
@Api("userController相关API")
@RestController
@RequestMapping(value = "/user/")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private ISmsSenderService smsSenderService;

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestParam("account") String account,
                        @RequestParam("password") String password) {
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(password).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = userService.selectByPhoneAndPwd(account, encryptPassword);
        if (!ObjectUtils.isEmpty(user)) {
            return new Result(200, "Login success", JWTUtil.sign(account,encryptPassword));
        } else {
            throw new BusinessException(ResultCode.LOGIN_FAIL.getCode(), ResultCode.LOGIN_FAIL.getMsg());
        }
    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterRequest request) {
        SmsRecord smsRecord = smsSenderService.checkVerifyCode(request.getPhone(), request.getVerifyCode());
        if (null == smsRecord) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR.getCode(), ResultCode.VERIFY_CODE_ERROR.getMsg());
        }
        if (!request.getPassword().trim().equals(request.getConfirmPassword())) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        User user = new User();
        user.setAccount(request.getAccount());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType());
        user.setUserName(request.getUserName());
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(user.getPassword()).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        user.setPassword(encryptPassword);
        user.setCreateTime(new Date());
        user.setStatus(Byte.valueOf("1"));
        userService.register(user);
        return new Result(200, "Login success", JWTUtil.sign(request.getAccount(), encryptPassword));
    }

    @PostMapping("forgetPassword")
    public Result forgetPassword(
            @RequestParam("password") @NotNull String password,
            @RequestParam("confirmPassword") @NotNull String confirmPassword,
            @RequestParam("phone") @NotNull String phone,
            @RequestParam("verifyCode") @NotNull String verifyCode) {
        SmsRecord smsRecord = smsSenderService.checkVerifyCode(phone, verifyCode);
        if (null == smsRecord) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR.getCode(), ResultCode.VERIFY_CODE_ERROR.getMsg());
        }
        if (!password.trim().equals(confirmPassword)) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        User user = new User();
        user.setPassword(password);
        user.setPhone(phone);
        userService.register(user);
        return new Result(200, "Login success", JWTUtil.sign(phone, password));
    }

    /**
     * 更新用户资料
     *
     * @param email
     * @param sex
     * @param userId
     * @param headUrl
     * @param userName
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public Result updateUser(@RequestParam("email") String email,
                             @RequestParam("sex") String sex,
                             @RequestParam("userId") Long userId,
                             @RequestParam("headUrl") String headUrl,
                             @RequestParam("userName") String userName) {
        User user = new User();
        user.setEmail(email);
        user.setUserName(userName);
        user.setSex(sex);
        user.setUserId(userId);
        user.setHeadUrl(headUrl);
        userService.updateUserInfo(user);
        Map returnMap = new HashMap(6);
        returnMap.put("userName", user.getUserName());
        returnMap.put("header_url", user.getHeadUrl());
        return new Result(200, "修改密码成功", returnMap);
    }

    /**
     * 登录后修改密码
     *
     * @param phone
     * @param oldPassword
     * @param newPassword
     * @param confirmPwd
     * @return
     */
    @PostMapping("modifyPassword")
    public Result modifyPassword(
            @RequestParam("phone") @NotNull String phone,
            @RequestParam("oldPassword") @NotNull String oldPassword,
            @RequestParam("newPassword") @NotNull String newPassword,
            @RequestParam("confirmPwd") @NotNull String confirmPwd) {
        if (!newPassword.equals(confirmPwd)) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(oldPassword).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = userService.selectByPhoneAndPwd(phone, encryptPassword);
        if (user == null) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR.getCode(), ResultCode.PASSWORD_ERROR.getMsg());
        }
        HashRequest hashRequest_new = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(confirmPwd).build();
        String encryptPassword_new = hashService.computeHash(hashRequest_new).toHex();
        user.setPassword(encryptPassword_new);
        user.setUpdateTime(new Date());
        userService.updateUserInfo(user);
        Map returnMap = new HashMap(6);
        returnMap.put("userName", user.getUserName());
        returnMap.put("userId", user.getUserId());
        returnMap.put("userType", user.getUserType());
        returnMap.put("token", JWTUtil.sign(phone, encryptPassword_new));
        returnMap.put("phone", user.getPhone());
        returnMap.put("header_url", user.getHeadUrl());
        return new Result(200, "修改密码成功", returnMap);
    }
}
