package com.wisdomconstruction.wisdomConstruction.service.highmodulusBasePoint;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulusBasePoint.HighModulusBasePointDO;

import java.util.List;

public interface HighModulusBasePointService {

    /**
     * 获取高支模设备基准数据
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

    /**
     * 更新高支模基准点
     * @param serialNumber
     * @return
     */
    boolean updateBasePoint(String serialNumber);

    /**
     * 设置高支模基准点
     * @param serialNumber
     * @return
     */
    boolean setBasePoint(String serialNumber);

}
