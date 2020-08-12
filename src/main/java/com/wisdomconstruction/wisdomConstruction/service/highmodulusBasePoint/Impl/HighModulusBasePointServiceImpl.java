package com.wisdomconstruction.wisdomConstruction.service.highmodulusBasePoint.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulusBasePoint.HighModulusBasePointDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulusBasePoint.HighModulusBasePointDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulusBasePoint.HighModulusBasePointDAO;
import com.wisdomconstruction.wisdomConstruction.service.highmodulusBasePoint.HighModulusBasePointService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class HighModulusBasePointServiceImpl implements HighModulusBasePointService {
    @Autowired
    HighModulusBasePointDAO highModulusBasePointDAO;

    /**
     * 获取高支模设备基准数据
     * @param serialNumber
     * @return
     */
    @Override
    public HighModulusBasePointDTO getHighModulusBasePoint(String serialNumber) {
        return highModulusBasePointDAO.getHighModulusBasePoint(serialNumber);
    }

    /**
     * 根据项目编号获取高支模设备基准数据
     * @param projectNo
     * @return
     */
    @Override
    public List<HighModulusBasePointDTO> getHighModulusBasePoints(String projectNo) {
        return highModulusBasePointDAO.getHighModulusBasePoints(projectNo);
    }

    /**
     * 添加高支模设备基准数据
     * @param highModulusBasePointDTO
     * @return
     */
    @Override
    public boolean addHighModulusBasePoint(HighModulusBasePointDTO highModulusBasePointDTO) {
        return highModulusBasePointDAO.addHighModulusBasePoint(highModulusBasePointDTO);
    }

    /**
     * 更新高支模基准点
     * @param serialNumber
     * @return
     */
    @Override
    public boolean updateBasePoint(String serialNumber) {
        HighModulusBasePointDO highModulusBasePointDO = new HighModulusBasePointDO();
        highModulusBasePointDO.setHorizontalDisplacement("0mm")
                .setSettlementTemplate("0mm")
                .setPolingAngle("0°")
                .setHorizontalAngle("0°")
                .setBearing("0T")
                .setSerialNumber(serialNumber);
        return this.updataHighBasePoint(highModulusBasePointDO);
    }

    /**
     * 设置高支模基准点
     * @param serialNumber
     * @return
     */
    public boolean setBasePoint(String serialNumber) {
        HighModulusBasePointDO highModulusBasePointDO = new HighModulusBasePointDO();
        Wrapper wrapper = new EntityWrapper<HighModulusBasePointDO>();
        wrapper.eq(HighModulusBasePointDOPropertiesEnum.SERIAL_NUMBER.getColumn(), serialNumber)
                .orderBy(HighModulusBasePointDOPropertiesEnum.GMT_CREATE.getColumn(), false);
        List<HighModulusBasePointDO> list = highModulusBasePointDAO.selectList(3, wrapper);
        //计算平均值
        float horizontalDisplacement = 0;
        float settlementTemplate = 0;
        float polingAngle = 0;
        float horizontalAngle = 0;
        float bearing = 0;
        if (list.size() > 0) {
            for (HighModulusBasePointDO h : list) {
                horizontalDisplacement += Float.valueOf(h.getHorizontalDisplacement().substring(0, h.getHorizontalDisplacement().length() - 2));
                settlementTemplate += Float.valueOf(h.getSettlementTemplate().substring(0, h.getSettlementTemplate().length() - 2));
                polingAngle += Float.valueOf(h.getPolingAngle().substring(0, h.getPolingAngle().length() - 1));
                horizontalAngle += Float.valueOf(h.getHorizontalAngle().substring(0, h.getHorizontalAngle().length() - 1));
                bearing += Float.valueOf(h.getBearing().substring(0, h.getBearing().length() - 1));
            }
        }
        String horizontalDisplacementResult = this.average(horizontalDisplacement, 3) + "mm";
        String settlementTemplateResult = this.average(settlementTemplate, 3) + "mm";
        String polingAngleResult = this.average(polingAngle, 3) + "°";
        String horizontalAngleResult = this.average(horizontalAngle, 3) + "°";
        String bearingResult = this.average(bearing, 4) + "T";
        highModulusBasePointDO.setSerialNumber(serialNumber)
                .setHorizontalDisplacement(horizontalDisplacementResult)
                .setSettlementTemplate(settlementTemplateResult)
                .setPolingAngle(polingAngleResult)
                .setHorizontalAngle(horizontalAngleResult)
                .setBearing(bearingResult);
        return this.updataHighBasePoint(highModulusBasePointDO);
    }

    /**
     * 更新高支模基点数据
     * @param highModulusBasePointDO
     * @return
     */
    public boolean updataHighBasePoint( HighModulusBasePointDO highModulusBasePointDO){
        Wrapper wrapper = new EntityWrapper<HighModulusBasePointDO>();
        String serialNumber = highModulusBasePointDO.getSerialNumber();
        wrapper.eq(StringUtils.isNotEmpty(serialNumber), HighModulusBasePointDOPropertiesEnum.SERIAL_NUMBER.getColumn(),serialNumber);
        return highModulusBasePointDAO.update(highModulusBasePointDO, wrapper);
    }

    /**
     * 计算平均值，并保留相应位数
     * @param num
     * @param count
     * @return
     */
    public String average(float num,Integer count){
        BigDecimal bd1 = new BigDecimal(Double.valueOf(num));
        return bd1.setScale(count, RoundingMode.HALF_UP).toString();
    }


}