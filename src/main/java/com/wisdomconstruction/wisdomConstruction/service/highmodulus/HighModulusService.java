package com.wisdomconstruction.wisdomConstruction.service.highmodulus;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;

import java.util.List;

public interface HighModulusService {
    /**
     * 添加高支模设备实时数据
     * @param constructionHighModulusDTO
     * @return
     */
    boolean addHighModulus(ConstructionHighModulusDTO constructionHighModulusDTO);

    /**
     * 获取高支模设备实时数据
     * @param serialNumber
     * @return
     */
    List<ConstructionHighModulusDTO> getHighModulus(String serialNumber);

    /**
     * 获取异常高支模设备实时数据
     * @param serialNumber
     * @return
     */
    List<ConstructionHighModulusDTO> getAlertHighModulus(String serialNumber);

    /**
     * 获取当天报警次数
     * @param serialNumber
     * @return
     */
    Integer getAlertCount(String serialNumber);
}
