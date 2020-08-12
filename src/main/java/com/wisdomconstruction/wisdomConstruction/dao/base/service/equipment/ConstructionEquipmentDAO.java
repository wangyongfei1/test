package com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment;

import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public interface ConstructionEquipmentDAO extends BaseDAO<ConstructionEquipmentDO> {

    /**
     * 根据设备类型获取设备
     * @param deviceType
     * @param projectNo
     * @return
     */
    List<ConstructionEquipmentDTO> getEquipment(String deviceType, String projectNo);
}
