package com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold;

import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;

/**
 * @author wangyongfei
 * @date 2020/6/28 20:53
 */
public interface DustThresholdDAO extends BaseDAO<DustThresholdDO> {
    /**
     * 查询扬尘阈值
     * @param projectNo
     * @return
     */
    DustThresholdDO getDustThreshold(String projectNo);

    /**
     * 更新扬尘阈值
     * @param dustThresholdDO
     * @return
     */
    boolean updateDustThreshold(DustThresholdDO dustThresholdDO);

}
