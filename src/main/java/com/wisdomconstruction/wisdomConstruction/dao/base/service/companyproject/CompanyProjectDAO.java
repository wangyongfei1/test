package com.wisdomconstruction.wisdomConstruction.dao.base.service.companyproject;

import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.company.CompanyProjectInfoDO;

/**
 * @author wangyongfei
 * @date 2020/7/9 16:47
 */
public interface CompanyProjectDAO extends BaseDAO<CompanyProjectInfoDO> {

    /**
     * 查询企业编号与项目地址
     * @param projectNo
     * @return
     */
    CompanyProjectInfoDO getCompanyProjectInfoDO(String projectNo);
}