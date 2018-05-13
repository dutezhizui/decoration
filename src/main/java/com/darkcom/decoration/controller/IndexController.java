package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IWorkerService;
import com.darkcom.decoration.service.IindexService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 工人服务
 *
 * @author yaojy
 */
@Api("IndexController")
@RestController
@RequestMapping(value = "/index/")
public class IndexController {
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IindexService indexService;

    /**
     * 首页数据
     *
     * @return
     */
    @PostMapping("index")
    public Result getServiceByType() {
        Result result = new Result(200);
        Map indexData=indexService.getIndexData();
        result.setData(indexData);
        return result;
    }

}
