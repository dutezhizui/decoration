package com.darkcom.decoration.controller;

import com.alibaba.fastjson.JSON;
import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.constant.CommonConstants;
import com.darkcom.decoration.dto.request.AddAddressRequest;
import com.darkcom.decoration.dto.request.UpdateAddressRequest;
import com.darkcom.decoration.model.Address;
import com.darkcom.decoration.service.IAddressService;
import com.darkcom.decoration.utils.JWTUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 地址管理
 *
 * @author yjy
 */
@Api("AddressController")
@RestController
@RequestMapping(value = "/address/v1/")
public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private IAddressService addressService;

    /**
     * 添加地址
     *
     * @param addressRequest
     * @param request
     * @return
     */
    @PostMapping(value = "addAddress")
    public Result add(@RequestBody AddAddressRequest addressRequest, HttpServletRequest request) {
        logger.debug("增加地址,request={}", JSON.toJSONString(addressRequest));
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        Address address = new Address();
        address.setPhone(addressRequest.getPhone());
        address.setName(addressRequest.getName());
        address.setDetailAddress(addressRequest.getDetailAddress());
        address.setProvince(addressRequest.getRegion()[0]);
        address.setCity(addressRequest.getRegion()[1]);
        address.setArea(addressRequest.getRegion()[2]);
        address.setCreateTime(new Date());
        address.setIsDefault(addressRequest.getDefault());
        address.setAccount(account);
        if (addressRequest.getDefault() == true) {
            addressService.setAddressUndefault(account);
        }
        addressService.addAddress(address);
        return Result.succeed();
    }

    /**
     * 设置默认地址
     *
     * @param isDefault
     * @param addressId
     * @return
     */
    @PostMapping(value = "setDefaultAddress")
    public Result setDefaultAddress(@RequestParam("isDefault") boolean isDefault,
                                    @RequestParam("addressId") Integer addressId,
                                    HttpServletRequest request) {
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        if (isDefault == true) {
            addressService.setAddressUndefault(account);
        }
        addressService.setAddressDefault(addressId, isDefault);
        return Result.succeed();
    }

    /**
     * 更新地址信息
     *
     * @param addressRequest
     * @return
     */
    @PostMapping(value = "updateAddressInfo")
    public Result updateAddressInfo(@RequestBody UpdateAddressRequest addressRequest,
                                    HttpServletRequest request) {
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        logger.debug("增加地址,request={}", JSON.toJSONString(addressRequest));
        Address address = new Address();
        address.setId(addressRequest.getAddressId());
        address.setPhone(addressRequest.getPhone());
        address.setName(addressRequest.getName());
        address.setDetailAddress(addressRequest.getDetailAddress());
        address.setProvince(addressRequest.getRegion()[0]);
        address.setCity(addressRequest.getRegion()[1]);
        address.setArea(addressRequest.getRegion()[2]);
        address.setUpdateTime(new Date());
        address.setIsDefault(addressRequest.getDefault());
        address.setAccount(account);
        if (addressRequest.getDefault() == true) {
            addressService.setAddressUndefault(account);
        }
        addressService.updateAddressInfo(address);
        return Result.succeed();
    }

    /**
     * 获取用户地址列表
     */
    @GetMapping(value = "addressList")
    public Result getAddressList(HttpServletRequest request) {
        String account = JWTUtil.getUsername(request.getHeader(CommonConstants.AUTHORIZATION));
        List addressList = addressService.getAddressList(account);
        return Result.succeed(addressList);
    }

    /**
     * 根据ID查询地址详情
     * @param addressId
     * @return
     */
    @GetMapping(value = "getAddressById")
    public Result getAddressById(@RequestParam("addressId") Integer addressId){
        Address address = addressService.getAddressById(addressId);
        return Result.succeed(address);
    }
}
