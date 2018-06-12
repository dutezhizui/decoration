package com.darkcom.decoration.service;

import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author yaojy
 */
public interface IMaterialsService {

    BuildingMaterials getMaterialsById(Long materialsId);


    List<BuildingMaterials> getMaterialsList(Integer materialsType, Integer pageNum, Integer pageSize);
}
