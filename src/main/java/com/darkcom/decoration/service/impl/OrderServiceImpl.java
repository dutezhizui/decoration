package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.OrderMapper;
import com.darkcom.decoration.model.Order;
import com.darkcom.decoration.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojy
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List getOrderList(Long userId, Integer status) {
        return orderMapper.getOrderList(userId,status);
    }

    @Override
    public void insertOrder(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, Integer status) {

    }
}
