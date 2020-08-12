package com.wisdomconstruction.wisdomConstruction.service.user.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.user.CompanyUserInfoDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.user.UserInfoMapper;
import com.wisdomconstruction.wisdomConstruction.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userService")
public class UserServiceImpl implements UserService {


    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public List<CompanyUserInfoDTO> getAllUserInfo(String companyNo, String projectNo) {

        return userInfoMapper.getAllCompanyUserInfo(companyNo,projectNo);
    }
}
