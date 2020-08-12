package com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulus.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulus.ConstructionHighModulusDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.highmodulus.ConstructionHighModulusMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulus.ConstructionHighModulusDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 高支模设备表 服务实现类
 * </p>
 *
 * @author huangc
 * @since 2020-05-07
 */
@Service("constructionHighModulusDAO")
@Slf4j
public class ConstructionHighModulusDAOImpl extends BaseDAOImpl<ConstructionHighModulusMapper, ConstructionHighModulusDO> implements ConstructionHighModulusDAO {

    @Resource
    private ConstructionHighModulusMapper constructionHighModulusMapper;

    /**
     * 添加高支模数据
     * @param constructionHighModulusDTO
     * @return
     */
    @Override
    public boolean addHighModulus(ConstructionHighModulusDTO constructionHighModulusDTO) {
        ConstructionHighModulusDO constructionHighModulusDO = new ConstructionHighModulusDO();
        BeanUtils.copyProperties(constructionHighModulusDTO,constructionHighModulusDO);
        constructionHighModulusDO.setCreatorInfo();
        log.info("高支模do:{}",constructionHighModulusDO);
        return insert(constructionHighModulusDO);
    }

    /**
     * 获取高支模数据
     * @param serialNumber
     * @return
     */
    @Override
    public List<ConstructionHighModulusDTO> getHighModulus(String serialNumber) {
        return constructionHighModulusMapper.getHighModulus(serialNumber);
    }

    /**
     * 获取异常高支模设备实时数据
     * @param serialNumber
     * @return
     */
    @Override
    public List<ConstructionHighModulusDTO> getAlertHighModulus(String serialNumber) {
        return constructionHighModulusMapper.getAlertHighModulus(serialNumber);
    }

    /**
     * 获取当天报警次数
     * @param serialNumber
     * @return
     */
    @Override
    public Integer getAlertCount(String serialNumber) {
        return constructionHighModulusMapper.getAlertCount(serialNumber);
    }
}
