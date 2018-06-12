package com.darkcom.decoration.mapper;

import com.darkcom.decoration.model.BuildingMaterials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuildingMaterialsMapper {
    int deleteByPrimaryKey(Long materialsId);

    int insert(BuildingMaterials record);

    int insertSelective(BuildingMaterials record);

    BuildingMaterials selectByPrimaryKey(Long materialsId);

    int updateByPrimaryKeySelective(BuildingMaterials record);

    int updateByPrimaryKey(BuildingMaterials record);

    List getMaterialsList(@Param("materialsType") Integer materialsType);
}