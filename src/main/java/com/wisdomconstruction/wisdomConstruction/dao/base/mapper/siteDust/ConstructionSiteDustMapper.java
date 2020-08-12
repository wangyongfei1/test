package com.wisdomconstruction.wisdomConstruction.dao.base.mapper.siteDust;

import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.BaseDAOMapper;

/**
 * <p>
 * 扬尘设备表 Mapper 接口
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public interface ConstructionSiteDustMapper extends BaseDAOMapper<ConstructionSiteDustDO> {

    /**
     * 查询最新的扬尘数据
     * @param projectNo
     * @return
     */
    ConstructionSiteDustDO getConstructionSiteDust(String projectNo);
}
