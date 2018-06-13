package com.darkcom.decoration.controller;

import com.alibaba.fastjson.JSON;
import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.common.ResultCode;
import com.darkcom.decoration.common.enums.OrderStatusEnum;
import com.darkcom.decoration.constant.CommonConstants;
import com.darkcom.decoration.dto.request.CreateOrderRequest;
import com.darkcom.decoration.exception.BusinessException;
import com.darkcom.decoration.model.Order;
import com.darkcom.decoration.model.User;
import com.darkcom.decoration.service.IOrderService;
import com.darkcom.decoration.service.IUserService;
import com.darkcom.decoration.utils.IdUtils;
import com.darkcom.decoration.utils.JWTUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yaojy
 */
@Api("OrderController相关API")
@RestController
@RequestMapping(value = "/order/v1/")
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserService userService;

    /**
     * 创建订单
     *
     * @param createOrderRequest
     * @param request
     * @return
     */
    @PostMapping(value = "createOrder")
    public Result createOrder(@RequestBody CreateOrderRequest createOrderRequest, HttpServletRequest request) {
        logger.debug("【创建订单】，request={}" + JSON.toJSONString(createOrderRequest));
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        User user = userService.selectUserByAccount(account);
        if (null == user) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMsg());
        }
        Order order = new Order();
        BeanUtils.copyProperties(createOrderRequest, order);
        order.setUserId(user.getUserId());
        order.setOrderNo(IdUtils.generatorOrderId(createOrderRequest.getOrderType()));
        order.setCreateTime(new Date());
        order.setStatus(OrderStatusEnum.UNPAID.getIndex());
        order.setAccount(account);
        orderService.createOrder(order);
        return Result.succeed();
    }

    /**
     * 查询订单详情
     *
     * @param orderNo
     * @return
     */
    @GetMapping(value = "orderInfo")
    public Result getOrderById(@RequestParam("orderNo") String orderNo) {
        logger.debug("【查询订单详情】，request={}" + orderNo);
        Order order = orderService.getOrderById(orderNo);
        return Result.succeed(order);
    }

    /**
     * 订单列表
     *
     * @param status
     * @return
     */
    @GetMapping("orderList")
    public Result getOrderList(
                        @RequestParam("status") Integer status, HttpServletRequest request) {
        Result result = new Result(200);
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        List<Order> orderList = orderService.getOrderList(account, status);
        result.setData(orderList);
        return Result.succeed(orderList);
    }

}
