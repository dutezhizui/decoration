package com.darkcom.decoration.service;

import com.darkcom.decoration.model.Order;

import java.util.List;

/**
 * @author yaojy
 */
public interface IOrderService {

    List getOrderList(Long userId, Integer status);

    void insertOrder(Order order);

    void updateOrderStatus(Long orderId,Integer status);
}
