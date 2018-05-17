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

/**
 * 短信验证码
 *
 * @author yjy
 */
@Api("SmsController相关API")
@RestController
@RequestMapping(value = "/sms/v1/")
public class SmsController {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);
    @Autowired
    private ISmsSenderService smsSenderService;

    /**
     * 发送验证码
     *
     * @param phone
     * @param codeType
     * @return
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public Result sendSms(@RequestParam("phone") String phone, @RequestParam("codeType") Integer codeType) {
        logger.debug("【发送验证码】，phone={},codeType={}", phone, codeType);
        smsSenderService.sendSms(phone, codeType);
        return Result.succeed();
    }
}
