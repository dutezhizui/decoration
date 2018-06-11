package com.darkcom.decoration.service;

import com.darkcom.decoration.model.Address;

import java.util.List;

public interface IAddressService {

    void addAddress(Address address);

    void updateAddressInfo(Address address);

    void setAddressUndefault(String account);

    void setAddressDefault(Integer addressId, boolean isDefault);

    List getAddressList(String account);

    Address getAddressById(Integer addressId);
}
