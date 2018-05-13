package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.service.IOrderService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yaojy
 */
@Api("OrderController相关API")
@RestController
@RequestMapping(value = "/order/")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;

    /**
     * 订单列表
     *
     * @param userId
     * @param status
     * @return
     */
    @PostMapping("orderList")
    public Result login(@RequestParam("userId") Long userId,
                        @RequestParam("status") Integer status) {
        Result result=new Result(200);
        List orderList=orderService.getOrderList(userId,status);
        result.setData(orderList);
        return result;
    }

}
