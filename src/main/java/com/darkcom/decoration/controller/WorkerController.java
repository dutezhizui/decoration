package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IWorkerService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 工人服务
 *
 * @author yaojy
 */
@Api("WorkerController")
@RestController
@RequestMapping(value = "/worker/v1/")
public class WorkerController {
    public static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
    @Autowired
    private IWorkerService iWorkerService;

    /**
     * 工人(服务)列表
     *
     * @param serviceType
     * @return
     */
    @GetMapping("serviceList")
    public Result getServiceByType(@RequestParam("serviceType") Integer serviceType,
                                   @RequestParam("pageNum") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        logger.info("服务列表,request={}", serviceType);
        List<WorkerService> list = iWorkerService.getServiceList(serviceType, pageNum, pageSize);
        return Result.succeed(list);
    }

    /**
     * 服务详情
     *
     * @param serviceId
     * @return
     */
    @GetMapping("getServiceById")
    public Result getServiceById(@RequestParam("serviceId") Long serviceId) {
        WorkerService materials = iWorkerService.getServiceById(serviceId);
        return Result.succeed(materials);
    }

}
