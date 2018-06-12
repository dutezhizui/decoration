package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.WorkerServiceMapper;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IWorkerService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yaojy
 */
@Service
@Transactional
public class WorkerServiceImpl implements IWorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    @Autowired
    private WorkerServiceMapper serviceMapper;

    @Override
    public WorkerService getServiceById(Long serviceId) {
        return serviceMapper.selectByPrimaryKey(serviceId);
    }

    @Override
    public List<WorkerService> getServiceList(Integer serviceType, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        return serviceMapper.getServiceList(serviceType);
    }
}
