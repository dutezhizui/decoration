package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.service.ISmsSenderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("userController相关API")
@RestController
@RequestMapping(value = "/sms/")
public class SmsController {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
    @Autowired
    private ISmsSenderService smsSenderService;

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public Result sendSms(@RequestParam("phone") String phone) {
        Result result = new Result(200);
        smsSenderService.sendSms(phone);
        return result;
    }

    /**
     * 校验验证码
     *
     * @param phone
     * @param VerifyCode
     * @return
     */
    @RequestMapping(value = "checkVerifyCode", method = RequestMethod.POST)
    public Result checkVerifyCode(@RequestParam("phone") String phone, @RequestParam("verifyCode") String VerifyCode) {
        Result result = new Result(200);
        smsSenderService.sendSms(phone);
        return result;
    }
}
