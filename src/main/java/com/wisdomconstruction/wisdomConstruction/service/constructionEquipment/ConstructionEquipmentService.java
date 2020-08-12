package com.wisdomconstruction.wisdomConstruction.service.constructionEquipment;

import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;

import java.util.List;

public interface ConstructionEquipmentService {

    /**
     * 根据设备类型获取设备
     * @param deviceType
     * @param projectNo
     * @return
     */
    List<ConstructionEquipmentDTO> getEquipment(String deviceType, String projectNo);
    /**
     * 根据设备编号获取项目编号
     * @param dtumei
     * @returnI
     */
    ConstructionEquipmentDTO getProjectNoByEquipmentNo(String dtumei);
}
