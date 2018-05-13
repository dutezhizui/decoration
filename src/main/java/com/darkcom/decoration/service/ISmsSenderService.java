package com.darkcom.decoration.service;

import com.darkcom.decoration.model.SmsRecord;

/**
 * @author yaojy
 */
public interface ISmsSenderService {
    /**
     * 发送短信方法
     * @param phone
     */
    void sendSms(String phone);

    /**
     * 保存短信记录
     * @param smsRecord
     */
    void saveSmsRecord(SmsRecord smsRecord);

    /**
     * 校验验证码
     * @param phone
     * @param verifyCode
     * @return
     */
    SmsRecord checkVerifyCode(String phone, String verifyCode);
}
