package com.darkcom.decoration.service;

import com.darkcom.decoration.model.WorkerService;

import java.util.List;

/**
 * @author yaojy
 */
public interface IWorkerService {

    WorkerService getServiceById(Long serviceId);

    List<WorkerService> getServiceList(Integer serviceType, Integer pageNum, Integer pageSize);
}
