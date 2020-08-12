package com.wisdomconstruction.wisdomConstruction.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.exception.ConstructionBizException;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.enums.WindDirectionEnum;

import com.wisdomconstruction.wisdomConstruction.service.DmodelService;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DmodelServiceImpl implements DmodelService {

//    字节数组（需要发送的指令）
//    byte[] b = new byte[21];
//    b[0] = (byte) '$';//数据包头标识
//    b[1] = (byte) 'R';//读写操作类型
//    b[4] = (byte) 'b';//终端设备 SN Ascii的字符串
//    b[16] = (byte) 0x00;//低四位协议版本编号
//    b[17] = (byte) 0x0100;//命令编号
//    b[19] = Byte.valueOf(DmodelEnum.DUST.getDModel());//设备型号代码
//    b[20] = (byte) 0xfe;//包序列
//    b[2] = (byte) b.length;//数据长度（不包含数据CRC）
//    b[21] = Byte.valueOf(CRCCheck.Make_CRC(b));//包头校验值
//    String instruction = TcpSendTool.sendInstruction("", 1, b);
//    字节数组（需要发送的指令）
//    byte[] b = new byte[8];
//    b[0] = (byte) 0x01;//地址码
//    b[1] = (byte) 0x03;//功能码
//    b[2] = (byte) 0x01F4;//500以上寄存器
//    b[4] = (byte) 0x0B;//寄存器长度
//    b[6] = (byte) Integer.parseInt(CRCCheck.getLowCRC(b), 16);//校验码低位
//    b[7] = (byte) Integer.parseInt(CRCCheck.getHightCRC(b), 16); //校验码高位

    @Autowired
    private ConstructionSiteDustDAO constructionSiteDustDAO;
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;

    /**
     * 扬尘设备实时数据
     */
    @Override
    public List<ConstructionSiteDustDO> dust(String modelType, String programNo) {
        log.info("设备类型：" + modelType + " 项目编号：" + programNo);
        String hexString = "01 03 01 F4 00 0B 44 03";
        log.info("扬尘请求参数:" + hexString);
        //根据项目编号查询设备列表
        EntityWrapper<ConstructionEquipmentDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), programNo);
        List<ConstructionEquipmentDO> constructionSiteDustDOS = constructionEquipmentDAO.selectList(entityWrapper);
        List<ConstructionEquipmentDO> collect = constructionSiteDustDOS.stream()
                .filter(e -> String.valueOf(e.getDeviceType()).equals(modelType)).collect(Collectors.toList());
        List<ConstructionSiteDustDO> constructionSiteDustDOList = new ArrayList<>();
        if (collect != null) {
            collect.forEach(constructionEquipmentDO -> {
                String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(), hexString);
                if (!StringUtils.isEmpty(resultString)) {
                    ConstructionSiteDustDO constructionSiteDustDO = analysisData(resultString);
                    if (constructionSiteDustDO != null) {
                        constructionSiteDustDO.setProjectNo(programNo);
                        constructionSiteDustDO.setCreatorInfo();
                        constructionSiteDustDO.setModifierInfo();
                        boolean insert = constructionSiteDustDAO.insert(constructionSiteDustDO);
                        if (!insert) {
                            log.error("插入数据库失败");
                        }
                        constructionSiteDustDOList.add(constructionSiteDustDO);
                    }
                }
            });
        }
        return constructionSiteDustDOList;
    }
    /**
     * 解析数据
     *
     * @param resultString
     * @return
     */
    public static ConstructionSiteDustDO analysisData(String resultString) {
        try {
            log.info("扬尘返回数据:{}", resultString);
            resultString = resultString.replace(" ", "");
            //有效字节数
            String countString = resultString.substring(4, 6);
            int count = Integer.parseInt(countString, 16);
            //截取有效数据
            resultString = resultString.substring(6, 6 + count * 2);
            String regex = "(.{4})";
            String result = resultString.replaceAll(regex, "$1,");
            String[] split = result.split(",");
            log.info("有效参数个数:{}", split.length);
            if (split.length != 11) {
                throw new ConstructionBizException(BizCodeMsgEnum.GET_MESSAGE_ERROR);
            }
            //风速
            Integer windSpeedNumber = Integer.parseInt(split[0], 16);
            String windSpeed = windSpeedNumber / 10.0 + "m/s";
            //风向值
            Integer directionWindNumber = Integer.parseInt(split[2], 16);
            String windDirection = getWindDirection(directionWindNumber);
            //湿度
            Integer humidityNumber = Integer.parseInt(split[4], 16);
            String humidity = humidityNumber / 10.0 + "%RH";
            //温度
            int temperatureNumber = Integer.parseInt(split[5], 16);
            temperatureNumber = ((temperatureNumber & 0x8000) > 0) ? (temperatureNumber - 0x10000) : (temperatureNumber);
            String temperature = temperatureNumber / 10.0 + "℃";
            //噪音
            Integer noiseNumber = Integer.parseInt(split[6], 16);
            String noise = noiseNumber / 10.0 + "dB";
            //pm2.5
            Integer particulate25Number = Integer.parseInt(split[7], 16);
            String particulate25 = particulate25Number + "ug/m3";
            //pm2.5
            Integer particulate10Number = Integer.parseInt(split[8], 16);
            String particulate10 = particulate10Number + "ug/m3";
            //tsp
            Integer suspendedParticlesNumber = Integer.parseInt(split[9], 16);
            String suspendedParticles = suspendedParticlesNumber + "ug/m3";
            //气压
            Integer airPressureNumber = Integer.parseInt(split[10], 16);
            String airPressure = airPressureNumber / 10.0 + "Kpa";
            return new ConstructionSiteDustDO().setWindSpeed(windSpeed)
                    .setDirectionWind(windDirection).setHumidity(humidity)
                    .setTemperature(temperature).setNoise(noise).setParticulate10(particulate10)
                    .setParticulate25(particulate25).setSuspendedParticles(suspendedParticles)
                    .setAirPressure(airPressure);
        } catch (Exception e) {
            log.error("数据解析错误,错误原因:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 风向
     *
     * @param code
     * @return
     */
    private static String getWindDirection(int code) {
        String directionWind = "";
        for (WindDirectionEnum e : WindDirectionEnum.values()) {
            if (e.getCode() == code) {
                directionWind = e.getRemark();
                break;
            }
        }
        return directionWind;
    }

    /**
     * 塔机设备实时数据
     */
    @Override
    public void tower() {

    }

    /**
     * 施工升降机实时数据
     */
    @Override
    public void elevator() {

    }

    /**
     * 施工升降机实时数据
     */
    @Override
    public void electrical_fire() {

    }
}
