package com.darkcom.decoration.controller;

import com.darkcom.decoration.common.Result;
import com.darkcom.decoration.model.BuildingMaterials;
import com.darkcom.decoration.service.IMaterialsService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("materialsList")
    public Result getMaterialsByType(@RequestParam("materialsType") Integer materialsType,
                                     @RequestParam("pageNum") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<BuildingMaterials> list = materialsService.getMaterialsList(materialsType, pageNum, pageSize);

        return Result.succeed(list);
    }

    /**
     * 建筑材料
     *
     * @param materialsId
     * @return
     */
    @GetMapping("getMaterialsById")
    public Result getMaterialsById(@RequestParam("materialsId") Long materialsId) {
        BuildingMaterials materials = materialsService.getMaterialsById(materialsId);

        return Result.succeed(materials);
    }

}
