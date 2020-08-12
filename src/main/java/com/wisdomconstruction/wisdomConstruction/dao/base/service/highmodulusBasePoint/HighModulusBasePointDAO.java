package com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulusBasePoint;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulusBasePoint.HighModulusBasePointDO;

import java.util.List;


public interface HighModulusBasePointDAO extends BaseDAO<HighModulusBasePointDO> {
    /**
     * 获取高支模基准数据
     * @param serialNumber
     * @return
     */
    HighModulusBasePointDTO getHighModulusBasePoint(String serialNumber);

    /**
     * 根据项目编号获取高支模设备基准数据
     * @param projectNo
     * @return
     */
    List<HighModulusBasePointDTO> getHighModulusBasePoints(String projectNo);

    /**
     * 添加高支模设备基准数据
     * @param highModulusBasePointDTO
     * @return
     */
    boolean addHighModulusBasePoint(HighModulusBasePointDTO highModulusBasePointDTO);
}
