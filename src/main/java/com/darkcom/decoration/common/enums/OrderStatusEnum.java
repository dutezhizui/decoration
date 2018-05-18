package com.darkcom.decoration.common.enums;

/**
 * 订单状态
 * @author yjy
 */
public enum OrderStatusEnum {
    /**
     * 取消
     */
    CANCEL("CANCEL", -1),
    /**
     * 已支付
     */
    PAYOFF("PAYOFF", 1),
    /**
     * 未支付
     */
    UNPAID("UNPAID", 2);


    private String name;
    private int index;

    OrderStatusEnum(String name, int index) {
        this.index = index;
        this.name = name;
    }

    // 普通方法
    public static String getName(int index) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
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
