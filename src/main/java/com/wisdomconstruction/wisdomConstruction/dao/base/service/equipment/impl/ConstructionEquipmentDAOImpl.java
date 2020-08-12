package com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulus.ConstructionHighModulusDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.equipment.ConstructionEquipmentMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
@Service
public class ConstructionEquipmentDAOImpl extends BaseDAOImpl<ConstructionEquipmentMapper, ConstructionEquipmentDO> implements ConstructionEquipmentDAO {

    /**
     * 根据设备类型获取设备
     * @param deviceType
     * @param projectNo
     * @return
     */
    @Override
    public List<ConstructionEquipmentDTO> getEquipment(String deviceType, String projectNo) {
        List<ConstructionEquipmentDTO> constructionEquipmentDTOList = new ArrayList<>();
        Wrapper wrapper = new EntityWrapper<ConstructionHighModulusDO>();
        wrapper.eq(StringUtils.isNotEmpty(deviceType), ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(),deviceType)
                .eq(StringUtils.isNotEmpty(projectNo),ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(),projectNo);
        List<ConstructionEquipmentDO> constructionEquipmentDOS = selectList(wrapper);
       if(CollectionUtil.isNotEmpty(constructionEquipmentDOS)){
            constructionEquipmentDOS.forEach(constructionEquipmentDO -> {
                ConstructionEquipmentDTO constructionEquipmentDTO = new ConstructionEquipmentDTO();
                BeanUtils.copyProperties(constructionEquipmentDO,constructionEquipmentDTO);
                constructionEquipmentDTOList.add(constructionEquipmentDTO);
            });
       }
        return constructionEquipmentDTOList;
    }
}
