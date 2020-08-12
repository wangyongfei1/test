package com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulusBasePoint.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulusBasePoint.HighModulusBasePointDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulusBasePoint.HighModulusBasePointDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.highmodulusBasePoint.HighModulusBasePointMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulusBasePoint.HighModulusBasePointDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighModulusBasePointDAOImpl extends BaseDAOImpl<HighModulusBasePointMapper, HighModulusBasePointDO> implements HighModulusBasePointDAO {
    /**
     * 获取高支模设备基准数据
     * @param serialNumber
     * @return
     */
    @Override
    public HighModulusBasePointDTO getHighModulusBasePoint(String serialNumber) {
        HighModulusBasePointDTO highModulusBasePointDTO = new HighModulusBasePointDTO();
        Wrapper wrapper = new EntityWrapper<HighModulusBasePointDO>();
        wrapper.eq(StringUtils.isNotEmpty(serialNumber), HighModulusBasePointDOPropertiesEnum.SERIAL_NUMBER.getColumn(), serialNumber);
        HighModulusBasePointDO highModulusBasePointDO = selectOne(wrapper);
        BeanUtils.copyProperties(highModulusBasePointDO, highModulusBasePointDTO);
        return highModulusBasePointDTO;
    }

    /**
     * 根据项目编号获取高支模设备基准数据
     * @param projectNo
     * @return
     */
    @Override
    public List<HighModulusBasePointDTO> getHighModulusBasePoints(String projectNo) {
        Wrapper wrapper = new EntityWrapper<HighModulusBasePointDO>();
        wrapper.eq(StringUtils.isNotEmpty(projectNo), HighModulusBasePointDOPropertiesEnum.PROJECT_NO.getColumn(),projectNo);
        return selectList(wrapper);
    }

    /**
     * 添加高支模基准数据
     * @param highModulusBasePointDTO
     * @return
     */
    @Override
    public boolean addHighModulusBasePoint(HighModulusBasePointDTO highModulusBasePointDTO) {
        HighModulusBasePointDO highModulusBasePointDO = new HighModulusBasePointDO();
        BeanUtils.copyProperties(highModulusBasePointDTO,highModulusBasePointDO);
        return insert(highModulusBasePointDO);
    }
}