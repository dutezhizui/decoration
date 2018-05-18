package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.dto.request.AddAddressRequest;
import com.darkcom.decoration.service.IAddressService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "addAddress")
    public Result add(@RequestBody AddAddressRequest addressRequest, HttpServletRequest request){

        return Result.succeed();
    }
}
