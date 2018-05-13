package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.SmsRecordMapper;
import com.darkcom.decoration.model.SmsRecord;
import com.darkcom.decoration.service.ISmsSenderService;
import com.darkcom.decoration.utils.VerifyUtil;
import com.montnets.mwgate.common.GlobalParams;
import com.montnets.mwgate.common.Message;
import com.montnets.mwgate.smsutil.ConfigManager;
import com.montnets.mwgate.smsutil.SmsSendConn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yaojy
 */
@Service
public class SmsSenderServiceImpl implements ISmsSenderService {
    private static final Logger logger = LoggerFactory.getLogger(SmsSenderServiceImpl.class);
    @Value("${sms.userName}")
    private String userName;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.masterIpAddress}")
    private String masterIpAddrss;
    @Value("${sms.requestPath}")
    private String requestPath;
    @Value("${sms.verifyCodeLength}")
    private int verifyCodeLength;
    @Autowired
    private SmsRecordMapper smsRecordMapper;


    @Override
    public void sendSms(String phone) {
        //创建全局参数
        GlobalParams globalParams = new GlobalParams();
        // 设置请求路径
        globalParams.setRequestPath(requestPath);
        // 设置是否需要日志 1:需要日志;0:不需要日志
        globalParams.setNeedLog(0);
        // 设置全局参数
        ConfigManager.setGlobalParams(globalParams);
        ConfigManager.setAccountInfo(userName, password, 1,
                masterIpAddrss, null, null, null);
        // 是否保持长连接
        boolean isKeepAlive = true;
        // 实例化短信处理对象
        SmsSendConn smsSendConn = new SmsSendConn(isKeepAlive);
        String verifyCode = VerifyUtil.getRandNum(verifyCodeLength);
        // 单条发送
        singleSend(smsSendConn, phone, verifyCode);
        //保存短信发送记录
        SmsRecord smsRecord=new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setVerifycode(verifyCode);
        smsRecord.setCreateTime(new Date());
        smsRecordMapper.insert(smsRecord);
    }

    /**
     * @param smsSendConn 短信处理对象,在这个方法中调用发送短信功能
     * @description 单条发送
     */
    private void singleSend(SmsSendConn smsSendConn, String phone, String verifyCode) {
        // 参数类
        Message message = new Message();
        // 设置用户账号 指定用户账号发送，需要填写用户账号，不指定用户账号发送，无需填写用户账号
        // 设置手机号码 此处只能设置一个手机号码
        message.setMobile(phone);
        // 设置内容
        message.setContent("您的验证码是" + verifyCode + "，在30分钟内有效。如非本人操作请忽略本短信。");
        // 设置扩展号
        message.setExno("11");
        // 用户自定义流水编号
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMddhhmmssSSS");
        message.setCustid(simpleDateFormat.format(new Date()));
        // 自定义扩展数据
        message.setExdata("abcdef");
        // 业务类型
        message.setSvrtype("SMS001");

        // 返回的流水号
        StringBuffer returnValue = new StringBuffer();
        // 返回值
        int result = -310099;
        // 发送短信
        result = smsSendConn.singleSend(message, returnValue);
    }

    @Override
    public void saveSmsRecord(SmsRecord smsRecord) {
        smsRecordMapper.insert(smsRecord);
    }

    @Override
    public SmsRecord checkVerifyCode(String phone, String verifyCode) {
        return smsRecordMapper.checkVerifyCode(phone,verifyCode);
    }
}
