package com.wisdomconstruction.wisdomConstruction.service.highmodulus.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.highmodulus.ConstructionHighModulusDAO;
import com.wisdomconstruction.wisdomConstruction.service.highmodulus.HighModulusService;
import com.wisdomconstruction.wisdomConstruction.service.highmodulusBasePoint.HighModulusBasePointService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Slf4j
@Service("highModulusService")
public class HighModulusServiceImpl implements HighModulusService {
    @Autowired
    private ConstructionHighModulusDAO constructionHighModulusDAO;
    @Autowired
    private HighModulusBasePointService highModulusBasePointService;

    /**
     * 添加高支模设备实时数据
     *
     * @param constructionHighModulusDTO
     * @return
     */
    @Override
    public boolean addHighModulus(ConstructionHighModulusDTO constructionHighModulusDTO) {
        StringBuilder sb = new StringBuilder("");
        HighModulusBasePointDTO highModulusBasePoint = highModulusBasePointService.getHighModulusBasePoint(constructionHighModulusDTO.getSerialNumber());
        if (highModulusBasePoint != null) {
            Integer height = highModulusBasePoint.getHeight();
            //水平位移
            String polingAngle = constructionHighModulusDTO.getPolingAngle();
            String horizontalDisplacement = calculationSin(polingAngle, height);
            String hDBasePoint = highModulusBasePoint.getHorizontalDisplacement();
            String horizontalDisplacement1 = hDBasePoint.substring(0, hDBasePoint.length() - 2);
            String horizontalDisplacementResult = absolute(horizontalDisplacement, horizontalDisplacement1, 3);

            Integer integer = check(horizontalDisplacementResult, highModulusBasePoint.getHorizontalDisplacementOffset());

            if (integer == 1) {
                sb.append("1,");
            } else if (integer == 2) {
                sb.append("2,");
            }

            //模板沉降
            String settlementTemplate = calculationCos(polingAngle, height);
            String sTBasePoint = highModulusBasePoint.getSettlementTemplate();
            String settlementTemplate1 = sTBasePoint.substring(0, sTBasePoint.length() - 2);
            String settlementTemplateResult = absolute(settlementTemplate, settlementTemplate1, 3);

            Integer integer1 = check(settlementTemplateResult, highModulusBasePoint.getSettlementTemplateOffset());

            if (integer1 == 1) {
                sb.append("3,");
            } else if (integer1 == 2) {
                sb.append("4,");
            }

            //立杆倾角
            String polingAngle1 = highModulusBasePoint.getPolingAngle();
            String polingAngle2 = polingAngle1.substring(0, polingAngle1.length() - 1);
            String polingAngleResult = absolute(polingAngle, polingAngle2, 3);
            Integer integer2 = check(polingAngleResult,  highModulusBasePoint.getPolingAngleOffset());
            if (integer2 == 1) {
                sb.append("5,");
            } else if (integer2 == 2) {
                sb.append("6,");
            }
            //水平杆倾角
            String hABasePoint = highModulusBasePoint.getHorizontalAngle();
            String horizontalAngle1 = hABasePoint.substring(0, hABasePoint.length() - 1);
            String horizontalAngle2 = constructionHighModulusDTO.getHorizontalAngle();
            String horizontalAngleResult = absolute(horizontalAngle2, horizontalAngle1, 3);
            Integer integer3 = check(horizontalAngleResult, highModulusBasePoint.getHorizontalAngleOffset());
            if (integer3 == 1) {
                sb.append("7,");
            } else if (integer3 == 2) {
                sb.append("8,");
            }
            //承重
            String bearingBasePoint = highModulusBasePoint.getBearing();
            String bearingBasePoint1 = bearingBasePoint.substring(0, bearingBasePoint.length() - 1);
            String bearing1 = constructionHighModulusDTO.getBearing();
            String bearingResult = absolute(bearing1, bearingBasePoint1, 4);
            Integer integer4 = check(bearingResult, highModulusBasePoint.getBearingOffset());
            if (integer4 == 1) {
                sb.append("9,");
            } else if (integer4 == 2) {
                sb.append("A,");
            }

            constructionHighModulusDTO.setHorizontalDisplacement(horizontalDisplacementResult + "mm")
                    .setSettlementTemplate(settlementTemplateResult + "mm")
                    .setPolingAngle(polingAngleResult + "°")
                    .setHorizontalAngle(horizontalAngleResult + "°")
                    .setBearing(bearingResult + "T");
            if (sb.toString().length() == 0) {
                constructionHighModulusDTO.setSiteStatus("0");
            } else {
                constructionHighModulusDTO.setSiteStatus(sb.toString().substring(0, sb.toString().length() - 1));
            }
            return constructionHighModulusDAO.addHighModulus(constructionHighModulusDTO);
        }
        log.info("添加高支模设备实时数据失败！原因：获取高支模"+constructionHighModulusDTO.getSerialNumber()+"的基准数据失败");
        return false;
    }

    /**
     * 获取高支模设备实时数据
     *
     * @param serialNumber
     * @return
     */
    @Override
    public List<ConstructionHighModulusDTO> getHighModulus(String serialNumber) {
        return constructionHighModulusDAO.getHighModulus(serialNumber);
    }

    /**
     * 获取异常高支模设备实时数据
     * @param serialNumber
     * @return
     */
    @Override
    public List<ConstructionHighModulusDTO> getAlertHighModulus(String serialNumber) {
        return constructionHighModulusDAO.getAlertHighModulus(serialNumber);
    }

    /**
     * 获取当天报警次数
     * @param serialNumber
     * @return
     */
    @Override
    public Integer getAlertCount(String serialNumber) {
        return constructionHighModulusDAO.getAlertCount(serialNumber);
    }

    /**
     * 校验是否超出设定范围
     *
     * @param target
     * @param offset
     * @return 0-正常; 1-预警； 2-报警
     */
    public static Integer check(String target, float offset) {
        float result = Float.valueOf(target);
        if (result > offset) {
            return 2;
        } else if (result == offset) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 计算变化量
     *
     * @param str
     * @param str1
     * @param count 保留的小数点位数
     * @return
     */
    public static String absolute(String str, String str1,Integer count) {
        float str1Result = Math.abs(Float.parseFloat(str1));
        float strResult = Math.abs(Float.valueOf(str));
        float abs = Math.abs((strResult - str1Result));
        BigDecimal bd = new BigDecimal(String.valueOf(abs));
        return bd.setScale(count, RoundingMode.HALF_UP).toString();
    }

    /**
     * 根据角度计算出水平位移
     *
     * @param str
     * @param height
     * @return
     */
    public static String calculationSin(String str, Integer height) {
        double polingAngle = Double.valueOf(str);
        double sin = Math.sin(Math.toRadians(polingAngle));
        String numberStr = String.valueOf(height * sin * 1000);
        BigDecimal bd = new BigDecimal(numberStr);
        return bd.setScale(3, RoundingMode.HALF_UP).toString();
    }

    /**
     * 根据角度计算出模板沉降
     *
     * @param str
     * @param height
     * @return
     */
    public static String calculationCos(String str, Integer height) {
        double polingAngle = Double.valueOf(str);
        double cos = Math.cos(Math.toRadians(polingAngle));
        String numberStr1 = String.valueOf(height * (1 - cos) * 1000);
        BigDecimal bd1 = new BigDecimal(numberStr1);
        return bd1.setScale(3, RoundingMode.HALF_UP).toString();
    }
}