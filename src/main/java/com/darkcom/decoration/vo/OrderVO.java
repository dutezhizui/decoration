package com.darkcom.decoration.vo;

import com.darkcom.decoration.model.Order;

public class OrderVO extends Order {
    /**
     * 商品名称
     */
    private String goodsName;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
