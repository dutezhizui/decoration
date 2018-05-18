package com.darkcom.decoration.mapper;

import com.darkcom.decoration.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List getOrderList(@Param("account") String account, @Param("status") Integer status);

    Order selectByOrderNo(@Param("orderNo") String orderNo);
}