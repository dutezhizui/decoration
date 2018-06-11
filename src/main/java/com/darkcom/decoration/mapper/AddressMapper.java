package com.darkcom.decoration.mapper;

import com.darkcom.decoration.model.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    void setAddressUndefault(@Param("account") String account);

    void setAddressDefault(@Param("addressId") Integer addressId, @Param("isDefault") boolean isDefault);

    List getAddressList(@Param("account") String account);
}