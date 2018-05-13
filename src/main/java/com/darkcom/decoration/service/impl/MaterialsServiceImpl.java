package com.darkcom.decoration.service.impl;

import com.darkcom.decoration.mapper.BuildingMaterialsMapper;
import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.service.IMaterialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaojy
 */
@Service
public class MaterialsServiceImpl implements IMaterialsService {
    private static final Logger logger = LoggerFactory.getLogger(MaterialsServiceImpl.class);
    @Autowired
    private BuildingMaterialsMapper materialsMapper;

    @Override
    public List getMaterials(String materialsType) {
        return materialsMapper.getMaterials(materialsType);
    }

    @Override
    public BuildingMaterials getMaterialsById(Long materialsId) {
        return materialsMapper.selectByPrimaryKey(materialsId);
    }

    @Override
    public void deployMaterials(BuildingMaterials buildingMaterials) {
        materialsMapper.insertSelective(buildingMaterials);
    }
}
