package com.darkcom.decoration.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ID生成器
 *
 * @author yjy
 */
public class IdUtils {
    private IdUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成订单号：channelId+yyyyMMddhhmissSSS+随机数
     *
     * @param orderType
     */
    public static String generatorOrderId(Integer orderType) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuilder sb = new StringBuilder();

        String dateString = df.format(date);
        sb.append(orderType).append(dateString).append(uuid.substring(uuid.length() - 4, uuid.length()).toUpperCase());
        return sb.toString();
    }
}
