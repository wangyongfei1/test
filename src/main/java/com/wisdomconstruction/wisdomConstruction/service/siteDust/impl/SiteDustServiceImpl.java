package com.wisdomconstruction.wisdomConstruction.service.siteDust.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.constant.RedisConsts;
import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import com.wisdomconstruction.wisdomConstruction.common.exception.ConstructionBizException;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.siteDust.ConstructionSiteDustDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.enums.DustGetDataMethodEnum;
import com.wisdomconstruction.wisdomConstruction.enums.WindDirectionEnum;
import com.wisdomconstruction.wisdomConstruction.service.intelligentspray.IntelligentSprayService;
import com.wisdomconstruction.wisdomConstruction.service.siteDust.SiteDustService;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.tool.RedisTool;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import com.wisdomconstruction.wisdomConstruction.tool.websocket.WebSocketServer;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.SendMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author kongke
 * @version 1.0
 * @intention
 * @date 2020/5/7 14:21
 */
@Service
@Slf4j
public class SiteDustServiceImpl implements SiteDustService {

    @Autowired
    private ConstructionSiteDustDAO constructionSiteDustDAO;
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;
    @Autowired
    private DustThresholdDAO dustThresholdDAO;
    @Autowired
    private SprayService sprayService;


    @Override
    public Map<?, ?> listAirQuality(String projectNo, Integer isInWeek) {
        String key = isInWeek == 1 ? RedisConsts.weekAirQuality.replace("{projectNo}", projectNo) :
                RedisConsts.monthAirQuality.replace("{projectNo}", projectNo);
        Map<Object, Object> saveMap = RedisTool.getHash(key);
        if (CollectionUtil.isNotEmpty(saveMap)) {
            return saveMap;
        }

        // 查询出一个月或者一周内的空气质量
        List<ConstructionSiteDustDO> constructionSiteDustDTOList =
                constructionSiteDustDAO.selectList(new EntityWrapper<ConstructionSiteDustDO>()
                .eq(ConstructionSiteDustDOPropertiesEnum.PROJECT_NO.getColumn(), projectNo)
                .gt(ConstructionSiteDustDOPropertiesEnum.GMT_CREATE.getColumn(),
                        isInWeek == 1 ? DateUtil.lastWeek() : DateUtil.lastMonth()));

        // 每天的空气质量
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile("^\\d+");

        Map<String, List<ConstructionSiteDustDO>> aqiByDay = constructionSiteDustDTOList.stream()
                // 每天的对应的数据
                .collect(Collectors.groupingBy(item -> DateUtil.format(item.getGmtCreate(), "MM-dd")));
        for (Map.Entry<String, List<ConstructionSiteDustDO>> entry : aqiByDay.entrySet()) {
            // 选择每天三指标中最大值的平均值
            double avg = entry.getValue().stream().mapToDouble(c -> {
                float pm25 = transfer(pattern, c.getParticulate25());
                float pm10 = transfer(pattern, c.getParticulate10());
                float suspend = transfer(pattern, c.getSuspendedParticles());
                return Math.max(Math.max(pm25, pm10), Math.max(pm10, suspend));
            }).average().orElse(0);
            map.put(entry.getKey(), String.valueOf(Math.round(avg)));
        }
        RedisTool.setHash(key, map, 1L, TimeUnit.HOURS);

        return map;
    }

    @Override
    public List<ConstructionSiteDustDTO> dust(String modelType, String programNo) {
        List<ConstructionSiteDustDTO> constructionSiteDustDTOList = new ArrayList<>();
        //根据项目编号查询设备列表
        EntityWrapper<ConstructionEquipmentDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), programNo)
                .eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), modelType);
        List<ConstructionEquipmentDO> constructionSiteDustDOS = constructionEquipmentDAO.selectList(entityWrapper);
        if(CollectionUtil.isNotEmpty(constructionSiteDustDOS)) {
            if (constructionSiteDustDOS.get(0).getGetDataMethod().equals
                    (DustGetDataMethodEnum.AUTOMATIC_RECEPTION.getMethodNo())) {
                ConstructionSiteDustDO constructionSiteDust = constructionSiteDustDAO.getConstructionSiteDust(programNo);
                if(ObjectUtils.isNotEmpty(constructionSiteDust)){
                    ConstructionSiteDustDTO constructionSiteDustDTO = new ConstructionSiteDustDTO();
                    BeanUtils.copyProperties(constructionSiteDust, constructionSiteDustDTO);
                    constructionSiteDustDTOList.add(constructionSiteDustDTO);
                    //校验扬尘数据是否超过阈值
                    checkDust(constructionSiteDust);
                    return constructionSiteDustDTOList;
                }
            } else if (constructionSiteDustDOS.get(0).getGetDataMethod().equals
                    (DustGetDataMethodEnum.ACTIVE_ACCESS.getMethodNo())) {
                log.info("设备类型：" + modelType + " 项目编号：" + programNo);
                String hexString = "01 03 01 F4 00 0B 44 03";
                log.info("扬尘请求参数:" + hexString);
                List<ConstructionEquipmentDO> collect = constructionSiteDustDOS.stream()
                        .filter(e -> String.valueOf(e.getDeviceType()).equals(modelType)).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(collect)) {
                    collect.forEach(constructionEquipmentDO -> {
                        String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(), hexString);
                        if (!StringUtils.isEmpty(resultString)) {
                            ConstructionSiteDustDO constructionSiteDustDO = analysisData(resultString);
                            if (constructionSiteDustDO != null) {
                                constructionSiteDustDO.setProjectNo(programNo);
                                constructionSiteDustDO.setDeviceNo(constructionEquipmentDO.getDtuImei());
                                constructionSiteDustDO.setCreatorInfo();
                                constructionEquipmentDO.setGmtCreate(new Date());
                                constructionSiteDustDO.setModifierInfo();
                                //校验扬尘数据是否超过阈值
                                checkDust(constructionSiteDustDO);
                                checkDustAndSendMessage(constructionSiteDustDO);
                                boolean insert = constructionSiteDustDAO.insert(constructionSiteDustDO);
                                if (!insert) {
                                    log.error("插入数据库失败");
                                }
                                // 处理页面需要展示出来的其他数据
                                ConstructionSiteDustDTO constructionSiteDustDTO = renderDust(constructionSiteDustDO);
                                constructionSiteDustDTOList.add(constructionSiteDustDTO);
                            }
                        }
                    });
                }
            }
        }
        return constructionSiteDustDTOList;
    }


    /**
     * AT指令控制DTU
     *
     * @param at       指令
     * @param dtuImei  设备imei
     * @return String
     */
    @Override
    public String atInstruction(String at, String dtuImei) {
        log.info("at is : " + at);
        return new ServiceSocket().sendDTU(dtuImei,at);
    }

    /**
     * 一周内的数据展示
     * @param constructionSiteDustDO
     * @return
     */
    public ConstructionSiteDustDTO renderDust(ConstructionSiteDustDO constructionSiteDustDO) {
        ConstructionSiteDustDTO data = new ConstructionSiteDustDTO();
        String projectNo = constructionSiteDustDO.getProjectNo();

        String key = RedisConsts.weekAirData.replace("{projectNo}", projectNo);
        ConstructionSiteDustDTO saveData = RedisTool.getVal(key, ConstructionSiteDustDTO.class);
        if (Objects.nonNull(saveData)) {
            BeanUtil.copyProperties(saveData, data);
        }
        else {
            // 选择一周内的所有数据
            List<ConstructionSiteDustDO> constructionSiteDustDOS =
                    constructionSiteDustDAO.selectList(new EntityWrapper<ConstructionSiteDustDO>()
                            .eq(ConstructionSiteDustDOPropertiesEnum.PROJECT_NO.getColumn(), projectNo)
                            .gt(ConstructionSiteDustDOPropertiesEnum.GMT_CREATE.getColumn(), DateUtil.lastWeek()));

            // 按天进行分组
            Map<String, List<ConstructionSiteDustDO>> dataInOneDay = constructionSiteDustDOS.stream()
                    .collect(Collectors.groupingBy(item -> DateUtil.format(item.getGmtCreate(), "MM-dd")));

            Map<String, Float> temperatureInWeek = new HashMap<>();
            Map<String, Float> humidityInWeek = new HashMap<>();
            Map<String, Float> noiseInWeek = new HashMap<>();
            Map<String, Float> windSpeedInWeek = new HashMap<>();
            Map<String, Float> pm25InWeek = new HashMap<>();
            Map<String, Float> pm10InWeek = new HashMap<>();

            // 处理每一天的数据，计算平均值
            Pattern pattern = Pattern.compile("^\\d+");
            for (Map.Entry<String, List<ConstructionSiteDustDO>> integerListEntry : dataInOneDay.entrySet()) {
                String date = integerListEntry.getKey();
                List<ConstructionSiteDustDO> dataList = integerListEntry.getValue();

                temperatureInWeek.put(date,
                        (float) dataList.stream().mapToDouble(tem -> transfer(pattern, tem.getTemperature()))
                                .average().orElse(0));
                humidityInWeek.put(date,
                        (float) dataList.stream().mapToDouble(hum -> transfer(pattern, hum.getHumidity()))
                                .average().orElse(0));
                noiseInWeek.put(date,
                        (float) dataList.stream().mapToDouble(noi -> transfer(pattern, noi.getNoise()))
                                .average().orElse(0));
                windSpeedInWeek.put(date,
                        (float) dataList.stream().mapToDouble(win -> transfer(pattern, win.getWindSpeed()))
                                .average().orElse(0));
                pm25InWeek.put(date,
                        (float) dataList.stream().mapToDouble(pm25 -> transfer(pattern, pm25.getParticulate25()))
                                .average().orElse(0));
                pm10InWeek.put(date,
                        (float) dataList.stream().mapToDouble(pm10 -> transfer(pattern, pm10.getParticulate10()))
                                .average().orElse(0));
            }
            data.setTemperatureInWeek(temperatureInWeek)
                    .setHumidityInWeek(humidityInWeek)
                    .setNoiseInWeek(noiseInWeek)
                    .setWindSpeedInWeek(windSpeedInWeek)
                    .setPm25InWeek(pm25InWeek)
                    .setPm10InWeek(pm10InWeek);
            RedisTool.setVal(key, data, 3L, TimeUnit.HOURS);
        }

        BeanUtil.copyProperties(constructionSiteDustDO, data);
        return data;
    }

    private Float transfer(Pattern pattern, String data) {
        Matcher matcher = pattern.matcher(data);
        if (! matcher.find()) {
            return 0F;
        }
        return Float.parseFloat(matcher.group());
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
     * 校验扬尘数据
     * @param constructionSiteDustDO
     */
    private synchronized void checkDust(ConstructionSiteDustDO constructionSiteDustDO) {
        try {
            String projectNo = constructionSiteDustDO.getProjectNo();
            DustThresholdDO dustThreshold = dustThresholdDAO.getDustThreshold(projectNo);
            if (ObjectUtils.isNotEmpty(dustThreshold) && dustThreshold.getPowerSwitch() == 1) {
                boolean particulate25 = isGreaterThreshold(constructionSiteDustDO.getParticulate25(),
                        dustThreshold.getParticulate25Threshold());
                boolean particulate10 = isGreaterThreshold(constructionSiteDustDO.getParticulate10(),
                        dustThreshold.getParticulate10Threshold());
                if (particulate25 || particulate10) {
                    boolean result = sprayService.openTheValve(projectNo, 1);
                    if(result){
                        boolean isUpdate = updateSwitchStatus(projectNo, 1);
                        if(isUpdate){
                            sendMessage("1,"+constructionSiteDustDO.getParticulate25()+","+
                                    constructionSiteDustDO.getParticulate10(), projectNo);
                        }
                    }
                }else {
                    if(dustThreshold.getSwitchStatus() == 0){
                        sendMessage("0,"+constructionSiteDustDO.getParticulate25()+","+
                                constructionSiteDustDO.getParticulate10(), projectNo);
                    }else {
                        boolean result = sprayService.openTheValve(projectNo, 0);
                        if(result){
                            boolean isUpdate = updateSwitchStatus(projectNo, 0);
                            if(isUpdate){
                                sendMessage("0,"+constructionSiteDustDO.getParticulate25()+","+
                                        constructionSiteDustDO.getParticulate10(), projectNo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验扬尘数据并推送
     * @param constructionSiteDustDO
     */
    private void checkDustAndSendMessage(ConstructionSiteDustDO constructionSiteDustDO) {
        try {
            String projectNo = constructionSiteDustDO.getProjectNo();
            DustThresholdDO dustThreshold = dustThresholdDAO.getDustThreshold(projectNo);
            if (ObjectUtils.isNotEmpty(dustThreshold)) {
                boolean particulate25 = isGreaterThreshold(constructionSiteDustDO.getParticulate25(),
                        dustThreshold.getParticulate25Threshold());
                boolean particulate10 = isGreaterThreshold(constructionSiteDustDO.getParticulate10(),
                        dustThreshold.getParticulate10Threshold());
                if (particulate25 || particulate10) {
                    //发消息
                    SendMessageUtils.SendMessage(projectNo,constructionSiteDustDO.getDeviceNo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验是否大于阈值
     * @param target
     * @param threshold 阈值
     * @return
     */
    private boolean isGreaterThreshold(String target,Float threshold){
        String substring = target.substring(0, target.length() - 5);
        if(Float.valueOf(substring)>=threshold)
            return true;
        return false;
    }

    /**
     * 设置开关状态
     * @param projectNo
     * @param switchStatus
     * @return
     */
    private boolean updateSwitchStatus(String projectNo,Integer switchStatus){
        DustThresholdDO dustThresholdDO = new DustThresholdDO();
        dustThresholdDO.setProjectNo(projectNo)
                .setSwitchStatus(switchStatus);
        return dustThresholdDAO.updateDustThreshold(dustThresholdDO);
    }

    /**
     * 发送消息
     * @param message 消息内容
     * @param projectNo 项目编号
     */
    private void sendMessage(String message,String projectNo){
        try {
            WebSocketServer.sendInfo(message, projectNo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
