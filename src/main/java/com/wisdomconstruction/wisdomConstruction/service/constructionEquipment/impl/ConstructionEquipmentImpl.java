package com.wisdomconstruction.wisdomConstruction.service.constructionEquipment.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.service.constructionEquipment.ConstructionEquipmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备服务类
 */
@Service
public class ConstructionEquipmentImpl implements ConstructionEquipmentService {

    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;

    /**
     * 根据设备类型获取设备
     * @param deviceType
     * @param projectNo
     * @return
     */
    @Override
    public List<ConstructionEquipmentDTO> getEquipment(String deviceType, String projectNo) {
        return constructionEquipmentDAO.getEquipment(deviceType,projectNo);
    }

    /**
     * 根据设备编号获取项目编号
     * @param dtumei
     * @returnI
     */
    @Override
    public ConstructionEquipmentDTO getProjectNoByEquipmentNo(String dtumei) {
        ConstructionEquipmentDO constructionEquipmentDO = constructionEquipmentDAO.selectOne(new EntityWrapper<ConstructionEquipmentDO>()
                .eq(ConstructionEquipmentDOPropertiesEnum.DTU_IMEI.getColumn(), dtumei));
        if(constructionEquipmentDO!=null){
            ConstructionEquipmentDTO constructionEquipmentDTO = new ConstructionEquipmentDTO();
            BeanUtils.copyProperties(constructionEquipmentDO,constructionEquipmentDTO);
            System.out.println("master");
            System.out.println("master");
            System.out.println("master");
            System.out.println("master");
            System.out.println("test");
            return constructionEquipmentDTO;
        }
        return null;
    }
}