package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.model.WorkerService;
import com.darkcom.decoration.service.IMaterialsService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 建材
 *
 * @author yaojy
 */
@Api("MaterialsController")
@RestController
@RequestMapping(value = "/materials/v1/")
public class MaterialsController {
    public static final Logger logger = LoggerFactory.getLogger(MaterialsController.class);
    @Autowired
    private IMaterialsService materialsService;

    /**
     * 建筑材料
     *
     * @param materialsType
     * @return
     */
    @PostMapping("materials")
    public Result getMaterialsByType(@RequestParam("materialsType") String materialsType) {
        Result result = new Result(200);
        List<BuildingMaterials> list = materialsService.getMaterials(materialsType);

        result.setData(list);
        return result;
    }

    /**
     * 建筑材料
     *
     * @param materialsId
     * @return
     */
    @PostMapping("getMaterialsById")
    public Result getMaterialsById(@RequestParam("materialsId") Long materialsId) {
        Result result = new Result(200);
        BuildingMaterials materials = materialsService.getMaterialsById(materialsId);

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
    @PostMapping("deployMaterials")
    public Result getMaterialsById(@RequestParam("userId") @NotNull Long userId,
                                   @RequestParam("materialsType") @NotNull Integer materialsType,
                                   @RequestParam("materialsName") @NotNull String materialsName,
                                   @RequestParam("province") String province,
                                   @RequestParam("city") String city,
                                   @RequestParam("area") String area,
                                   @RequestParam("price") Long price,
                                   @RequestParam("comment") String comment,
                                   @RequestParam("image") String image) {
        Result result = new Result(200);
        BuildingMaterials buildingMaterials=new BuildingMaterials();
        buildingMaterials.setCreator(userId);
        buildingMaterials.setCreateTime(new Date());
        buildingMaterials.setMaterialsType(materialsType);
        buildingMaterials.setMaterialsName(materialsName);
        buildingMaterials.setProvince(province);
        buildingMaterials.setCity(city);
        buildingMaterials.setArea(area);
        buildingMaterials.setPrice(price);
        buildingMaterials.setComment(comment);
        buildingMaterials.setImage(image);
        materialsService.deployMaterials(buildingMaterials);

        result.setData(buildingMaterials);
        return result;
    }
}
