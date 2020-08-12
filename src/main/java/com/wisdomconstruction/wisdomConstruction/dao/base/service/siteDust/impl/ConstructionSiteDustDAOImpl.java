package com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.impl;

import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.siteDust.ConstructionSiteDustMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 扬尘设备表 服务实现类
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
@Service
public class ConstructionSiteDustDAOImpl extends BaseDAOImpl<ConstructionSiteDustMapper, ConstructionSiteDustDO> implements ConstructionSiteDustDAO {

    @Resource
    private ConstructionSiteDustMapper constructionSiteDustMapper;

    /**
     * 获取最新的扬尘数据
     * @param projectNo
     * @return
     */
    @Override
    public ConstructionSiteDustDO getConstructionSiteDust(String projectNo) {
        return constructionSiteDustMapper.getConstructionSiteDust(projectNo);
    }
}
