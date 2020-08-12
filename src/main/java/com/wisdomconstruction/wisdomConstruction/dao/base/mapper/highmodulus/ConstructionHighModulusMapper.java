package com.wisdomconstruction.wisdomConstruction.dao.base.mapper.highmodulus;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.BaseDAOMapper;

import java.util.List;

/**
 * <p>
 * 高支模设备表 Mapper 接口
 * </p>
 *
 * @author huangc
 * @since 2020-05-07
 */
public interface ConstructionHighModulusMapper extends BaseDAOMapper<ConstructionHighModulusDO> {

    /**
     * 获取高支模实时数据
     * param highModulusVo
     * @returnT
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
