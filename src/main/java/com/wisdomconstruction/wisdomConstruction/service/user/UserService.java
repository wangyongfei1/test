package com.wisdomconstruction.wisdomConstruction.service.user;


import com.wisdomconstruction.wisdomConstruction.common.dto.user.CompanyUserInfoDTO;

import java.util.List;

public interface UserService {
    /**
     * 获取公司项目下所有用户
     * @param companyNo
     * @param projectNo
     * @return
     */
    List<CompanyUserInfoDTO> getAllUserInfo(String companyNo, String projectNo);
}
