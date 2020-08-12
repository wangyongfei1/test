package com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.dustthreshold.DustThresholdDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.dustthreshold.DustThresholdMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import org.springframework.stereotype.Repository;

/**
 * @author wangyongfei
 * @date 2020/6/28 20:54
 */
@Repository
public class DustThresholdDAOImpl extends BaseDAOImpl<DustThresholdMapper, DustThresholdDO> implements DustThresholdDAO {

    /**
     * 查询扬尘阈值
     * @param projectNo
     * @return
     */
    @Override
    public DustThresholdDO getDustThreshold(String projectNo) {
        return selectOne(new EntityWrapper<DustThresholdDO>()
                .eq(DustThresholdDOPropertiesEnum.PROJECT_NO.getColumn(), projectNo));
    }

    @Override
    public boolean updateDustThreshold(DustThresholdDO dustThresholdDO) {
        return update(dustThresholdDO,new EntityWrapper<DustThresholdDO>()
                .eq(DustThresholdDOPropertiesEnum.PROJECT_NO.getColumn(),dustThresholdDO.getProjectNo()));
    }
}