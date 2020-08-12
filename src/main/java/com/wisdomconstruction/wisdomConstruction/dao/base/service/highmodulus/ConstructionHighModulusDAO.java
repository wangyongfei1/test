package com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulus;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;

import java.util.List;

/**
 * <p>
 * 高支模设备表 服务类
 * </p>
 *
 * @author huangc
 * @since 2020-05-07
 */
public interface ConstructionHighModulusDAO extends BaseDAO<ConstructionHighModulusDO> {

    /**
     * 添加高支模数据
     * @param constructionHighModulusDTO
     * @return
     */
    boolean addHighModulus(ConstructionHighModulusDTO constructionHighModulusDTO);

    /**
     * 获取高支模数据
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
