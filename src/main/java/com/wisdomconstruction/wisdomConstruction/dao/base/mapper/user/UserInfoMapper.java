package com.wisdomconstruction.wisdomConstruction.dao.base.mapper.user;


import com.wisdomconstruction.wisdomConstruction.common.dto.user.CompanyUserInfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper{

    List<CompanyUserInfoDTO> getAllCompanyUserInfo(@Param("companyNo") String companyNo, @Param("projectNo")String projectNo);
}
