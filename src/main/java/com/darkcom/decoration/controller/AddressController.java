package com.darkcom.decoration.controller;

import com.alibaba.fastjson.JSON;
import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.dto.request.AddAddressRequest;
import com.darkcom.decoration.model.Address;
import com.darkcom.decoration.service.IAddressService;
import com.darkcom.decoration.utils.JWTUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 地址管理
 */
@Api("AddressController")
@RestController
@RequestMapping(value = "/address/v1/")
public class AddressController {
    private static final Logger logger= LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private IAddressService addressService;

    /**
     * 添加地址
     * @param addressRequest
     * @param request
     * @return
     */
    @PostMapping(value = "addAddress")
    public Result add(@RequestBody AddAddressRequest addressRequest, HttpServletRequest request){
        logger.debug("增加地址,request={}", JSON.toJSONString(addressRequest));
        String account = JWTUtil.getUsername(request.getHeader("authorization"));
        Address address=new Address();
        BeanUtils.copyProperties(addressRequest,address);
        address.setAccount(account);
        addressService.addAddress(address);
        return Result.succeed();
    }

    /**
     * 设置默认地址
     * @param isDefault
     * @param addressId
     * @return
     */
    @PostMapping(value = "setDefaultAddress")
    public Result setDefaultAddress(@RequestParam("isDefault") Integer isDefault,
                                    @RequestParam("addressId") Integer addressId){
        Address address=new Address();
        address.setIsDefault(isDefault);
        addressService.updateAddressInfo(address);
        return Result.succeed();
    }

    /**
     * 更新地址信息
     * @param isDefault
     * @param addressId
     * @param province
     * @param city
     * @param detailAddress
     * @param area
     * @return
     */
    @PostMapping(value = "updateAddressInfo")
    public Result updateAddressInfo(@RequestParam("isDefault") Integer isDefault,
                                    @RequestParam("addressId") Integer addressId,
                                    @RequestParam("province") String province,
                                    @RequestParam("city") String city,
                                    @RequestParam("detailAddress") String detailAddress,
                                    @RequestParam("area") String area){
        Address address=new Address();
        address.setIsDefault(isDefault);
        address.setProvince(province);
        address.setCity(city);
        address.setArea(area);
        address.setDetailAddress(detailAddress);
        addressService.updateAddressInfo(address);
        return Result.succeed();
    }
}
