package com.wisdomconstruction.wisdomConstruction.dao.base.service.companyproject.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.company.CompanyProjectInfoDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.companyproject.CompanyProjectInfoDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.companyproject.CompanyProjectMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.companyproject.CompanyProjectDAO;
import org.springframework.stereotype.Repository;

/**
 * @author wangyongfei
 * @date 2020/7/9 16:48
 */
@Repository("companyProjectDAO")
public class CompanyProjectDAOImpl extends BaseDAOImpl<CompanyProjectMapper, CompanyProjectInfoDO> implements CompanyProjectDAO {

    /**
     * 查询企业编号与项目地址
     * @param projectNo
     * @return
     */
    public CompanyProjectInfoDO getCompanyProjectInfoDO(String projectNo) {
        EntityWrapper<CompanyProjectInfoDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(CompanyProjectInfoDOPropertiesEnum.PROJECT_NO.getColumn(), projectNo);
        return selectOne(entityWrapper);
    }
}