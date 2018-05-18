package com.darkcom.decoration.common.enums;

import org.omg.CORBA.PUBLIC_MEMBER;

public enum PayTypeEnum {
    /**
     * 卡类型(1:支付宝,2:微信)
     */
    ONCE("ZFB", 1),
    CIRCLE("WX", 2);
    private String name;
    private int index;

    PayTypeEnum(String name, int index) {
        this.index = index;
        this.name = name;
    }

    // 普通方法
    public static String getName(int index) {
        for (PayTypeEnum c : PayTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
