package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IWorkerService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 工人服务
 *
 * @author yaojy
 */
@Api("WorkerController")
@RestController
@RequestMapping(value = "/worker/")
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
    @PostMapping("service")
    public Result getServiceByType(@RequestParam("serviceType") String serviceType) {
        Result result = new Result(200);
        List<WorkerService> list = iWorkerService.getService(serviceType);

        result.setData(list);
        return result;
    }

    /**
     * 服务详情
     *
     * @param serviceId
     * @return
     */
    @PostMapping("getServiceById")
    public Result getServiceById(@RequestParam("serviceId") Long serviceId) {
        Result result = new Result(200);
        WorkerService materials = iWorkerService.getServiceById(serviceId);

        result.setData(materials);
        return result;
    }

    /**
     * 发布服务
     *
     * @param userId
     * @param serviceType
     * @param serviceName
     * @param province
     * @param city
     * @param area
     * @param price
     * @param serviceDesc
     * @return
     */
    @PostMapping("deployService")
    public Result getMaterialsById(@RequestParam("userId") Long userId,
                                   @RequestParam("serviceType") Integer serviceType,
                                   @RequestParam("serviceName") String serviceName,
                                   @RequestParam("province") String province,
                                   @RequestParam("city") String city,
                                   @RequestParam("area") String area,
                                   @RequestParam("price") Long price,
                                   @RequestParam("serviceDesc") String serviceDesc) {
        Result result = new Result(200);
        WorkerService workerService=new WorkerService();
        workerService.setCreator(userId);
        workerService.setCreateTime(new Date());
        workerService.setServiceType(serviceType);
        workerService.setServiceName(serviceName);
        workerService.setProvince(province);
        workerService.setCity(city);
        workerService.setArea(area);
        workerService.setPrice(price);
        workerService.setServiceDesc(serviceDesc);
        iWorkerService.deployService(workerService);

        result.setData(workerService);
        return result;
    }

}
