package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.WorkerServiceMapper;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IWorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojy
 */
@Service
public class WorkerServiceImpl implements IWorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    @Autowired
    private WorkerServiceMapper serviceMapper;

    @Override
    public List getService(String serviceType) {
        return serviceMapper.getServices(serviceType);
    }

    @Override
    public WorkerService getServiceById(Long serviceId) {
        return serviceMapper.selectByPrimaryKey(serviceId);
    }

    @Override
    public void deployService(WorkerService workerService) {
        serviceMapper.insertSelective(workerService);
    }
}
