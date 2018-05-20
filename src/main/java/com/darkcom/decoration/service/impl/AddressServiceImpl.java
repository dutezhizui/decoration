package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.AddressMapper;
import com.darkcom.decoration.model.Address;
import com.darkcom.decoration.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地址管理
 * @author yjy
 */
@Service
public class AddressServiceImpl implements IAddressService{
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
}
