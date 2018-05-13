package com.darkcom.decoration.service;

import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author yaojy
 */
public interface IMaterialsService {
    /**
     * 获取用户
     * @param materialsType
     */
    List getMaterials(String materialsType);

    BuildingMaterials getMaterialsById(Long materialsId);

    void deployMaterials(BuildingMaterials buildingMaterials);
}
