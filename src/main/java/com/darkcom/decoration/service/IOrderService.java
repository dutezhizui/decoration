package com.darkcom.decoration.service;

import com.darkcom.decoration.model.Order;

import java.util.List;

/**
 * @author yaojy
 */
public interface IOrderService {

    List getOrderList(String account, Integer status);

    void createOrder(Order order);

    void updateOrderStatus(Long orderId, Integer status);

    Order getOrderById(String orderNo);
}
