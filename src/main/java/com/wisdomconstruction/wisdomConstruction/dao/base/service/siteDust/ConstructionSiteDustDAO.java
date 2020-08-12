package com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust;

import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;

/**
 * <p>
 * 扬尘设备表 服务类
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public interface ConstructionSiteDustDAO extends BaseDAO<ConstructionSiteDustDO> {

    /**
     * 获取扬尘数据
     * @param projectNo
     * @return
     */
    ConstructionSiteDustDO getConstructionSiteDust(String projectNo);

}
