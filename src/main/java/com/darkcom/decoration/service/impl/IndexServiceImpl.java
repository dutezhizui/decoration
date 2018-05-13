package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IMaterialsService;
import com.darkcom.decoration.service.IWorkerService;
import com.darkcom.decoration.service.IindexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaojy
 */
@Service
public class IndexServiceImpl implements IindexService {
    private static final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);
    @Autowired
    private IWorkerService workerService;
    @Autowired
    private IMaterialsService materialsService;

    @Override
    public Map getIndexData() {
        Map indexMap = new HashMap(2);
        List<WorkerService> services = workerService.getService("1");
        List<BuildingMaterials> materials = materialsService.getMaterials("1");
        indexMap.put("service", services);
        indexMap.put("materials", materials);
        return indexMap;
    }
}
