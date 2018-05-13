package com.darkcom.decoration.service;

import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.model.WorkerService;

import java.util.List;

/**
 * @author yaojy
 */
public interface IWorkerService {
    /**
     * 获取用户
     * @param serviceType
     */
    List getService(String serviceType);

    WorkerService getServiceById(Long serviceId);

    void deployService(WorkerService workerService);
}
