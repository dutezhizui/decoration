package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.common.ResultCode;
import com.darkcom.decoration.dto.request.ForgetPasswordRequest;
import com.darkcom.decoration.dto.request.ModifyPasswordRequest;
import com.darkcom.decoration.dto.request.UpdateUserRequest;
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

/**
 * @author yaojy
 */
@Api("userController相关API")
@RestController
@RequestMapping(value = "/user/v1/")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private ISmsSenderService smsSenderService;

    /**
     * 账号登录
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("commonLogin")
    public Result login(@RequestParam("account") String account,
                        @RequestParam("password") String password) {
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(password).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = userService.selectByPhoneAndPwd(account, encryptPassword);
        if (!ObjectUtils.isEmpty(user)) {
            return new Result(200, "Login success", JWTUtil.sign(account, encryptPassword));
        } else {
            throw new BusinessException(ResultCode.LOGIN_FAIL.getCode(), ResultCode.LOGIN_FAIL.getMsg());
        }
    }

    /**
     * 快速登录，手机号验证码登录
     *
     * @param phone
     * @param verifyCode
     * @return
     */
    @PostMapping("fastLogin")
    public Result fastLogin(@RequestParam("phone") @NotNull String phone, @RequestParam("verifyCode") @NotNull(message = "请输入验证码") String verifyCode) {
        User user = userService.selectUserByAccount(phone);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMsg());
        }
        SmsRecord smsRecord = smsSenderService.checkVerifyCode(phone, verifyCode, 1);
        if (ObjectUtils.isEmpty(smsRecord)) {
            throw new BusinessException(ResultCode.VERIFY_CODE_VALIDATE.getCode(), ResultCode.VERIFY_CODE_VALIDATE.getMsg());
        }
        return new Result(200, "Login success",JWTUtil.sign(user.getAccount(), user.getPassword()));
    }

    /**
     * 用户注册
     *
     * @return
     */
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterRequest request) {
        if (!request.getPassword().trim().equals(request.getConfirmPassword())) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        SmsRecord smsRecord = smsSenderService.checkVerifyCode(request.getPhone(), request.getVerifyCode(), 2);
        if (null == smsRecord) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR.getCode(), ResultCode.VERIFY_CODE_ERROR.getMsg());
        }
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(request.getPassword()).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = new User();
        user.setAccount(request.getAccount());
        user.setPhone(request.getPhone());
        user.setUserType(request.getUserType());
        user.setUserName(request.getUserName());
        user.setPassword(encryptPassword);
        user.setCreateTime(new Date());
        user.setStatus(Byte.valueOf("1"));
        userService.register(user);
        return new Result(200, "Login success", JWTUtil.sign(request.getAccount(), encryptPassword));
    }

    /**
     * 忘记密码
     *
     * @param request
     * @return
     */
    @PostMapping("forgetPassword")
    public Result forgetPassword(@RequestBody ForgetPasswordRequest request) {
        SmsRecord smsRecord = smsSenderService.checkVerifyCode(request.getPhone(), request.getVerifyCode(), 2);
        if (null == smsRecord) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR.getCode(), ResultCode.VERIFY_CODE_ERROR.getMsg());
        }
        if (!request.getPassword().trim().equals(request.getConfirmPassword())) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(request.getPassword()).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = new User();
        user.setPassword(encryptPassword);
        user.setPhone(request.getPhone());
        userService.updateUserInfo(user);
        return new Result(200, "Login success", JWTUtil.sign(request.getPassword(), encryptPassword));
    }

    /**
     * 更新用户资料
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    public Result updateUser(@RequestBody UpdateUserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUserName());
        user.setSex(request.getSex());
        user.setUserId(user.getUserId());
        user.setHeadUrl(user.getHeadUrl());
        userService.updateUserInfo(user);
        return Result.succeed();
    }

    /**
     * 登录后修改密码
     *
     * @param request
     * @return
     */
    @PostMapping("modifyPassword")
    public Result modifyPassword(@RequestBody ModifyPasswordRequest request) {
        if (!request.getConfirmPassword().equals(request.getNewPassword())) {
            throw new BusinessException(ResultCode.TWICE_PASSWORD_NOT_SAME.getCode(), ResultCode.TWICE_PASSWORD_NOT_SAME.getMsg());
        }
        DefaultHashService hashService = new DefaultHashService();
        HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(request.getOldPassword()).build();
        String encryptPassword = hashService.computeHash(hashRequest).toHex();
        User user = userService.selectByPhoneAndPwd(request.getPhone(), encryptPassword);
        if (user == null) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR.getCode(), ResultCode.PASSWORD_ERROR.getMsg());
        }
        HashRequest hashRequest_new = new HashRequest.Builder().setAlgorithmName("SHA-256").setSource(request.getConfirmPassword()).build();
        String encryptPassword_new = hashService.computeHash(hashRequest_new).toHex();
        user.setPassword(encryptPassword_new);
        user.setUpdateTime(new Date());
        userService.updateUserInfo(user);
        return new Result(200, "修改密码成功", JWTUtil.sign(request.getPhone(), encryptPassword_new));
    }
}
