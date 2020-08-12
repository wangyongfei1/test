package com.wisdomconstruction.wisdomConstruction.controller.equipment.Impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.controller.equipment.EquipmentApi;
import com.wisdomconstruction.wisdomConstruction.service.constructionEquipment.ConstructionEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EquipmentController implements EquipmentApi {
    @Autowired
    private ConstructionEquipmentService constructionEquipmentService;
    /**
     * 根据设备类型获取设备
     * @param  deviceType  设备类型
     * @param  projectNo  项目编号
     * @return
     */
    public List<ConstructionEquipmentDTO> getEquipment(@RequestParam String deviceType, @RequestParam String projectNo) {
        return constructionEquipmentService.getEquipment(deviceType, projectNo);
    }
}