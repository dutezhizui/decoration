package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.AddressMapper;
import com.darkcom.decoration.model.Address;
import com.darkcom.decoration.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地址管理
 *
 * @author yjy
 */
@Service
@Transactional
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void addAddress(Address address) {
        addressMapper.insertSelective(address);
    }

    @Override
    public void updateAddressInfo(Address address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }

    /**
     * 设置默认地址
     *
     * @param account
     */
    @Override
    public void setAddressUndefault(String account) {
        addressMapper.setAddressUndefault(account);
    }

    /**
     * 设置默认地址
     *
     * @param addressId
     */
    @Override
    public void setAddressDefault(Integer addressId,boolean isDefault) {
        addressMapper.setAddressDefault(addressId,isDefault);
    }

    /**
     * 获取地址列表
     * @param account
     * @return
     */
    @Override
    public List getAddressList(String account) {
        return addressMapper.getAddressList(account);
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return addressMapper.selectByPrimaryKey(addressId);
    }
}
