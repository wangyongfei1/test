package com.wisdomconstruction.wisdomConstruction.service.third.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.common.exception.ConstructionBizException;
import com.wisdomconstruction.wisdomConstruction.common.vo.third.ThirdApiVO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.discharge.ConstructionSiteDischargeDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.tower.ConstructionSiteTowerDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.user.DeviceUserInfoDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.user.DeviceUserInfoDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.discharge.ConstructionSiteDischargeDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.tower.ConstructionSiteTowerDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.user.DeviceUserInfoDAO;
import com.wisdomconstruction.wisdomConstruction.enums.PushEquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.service.constructionEquipment.ConstructionEquipmentService;
import com.wisdomconstruction.wisdomConstruction.service.intelligentspray.IntelligentSprayService;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.service.third.ThirdApiService;
import com.wisdomconstruction.wisdomConstruction.tool.JacksonTool;
import com.wisdomconstruction.wisdomConstruction.tool.MD5Tool;
import com.wisdomconstruction.wisdomConstruction.tool.ValidatorTool;
import com.wisdomconstruction.wisdomConstruction.tool.websocket.WebSocketServer;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.SendMessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据推送
 */
@Service
@Slf4j
public class ThirdApiServiceImpl implements ThirdApiService {

    @Autowired
    private DeviceUserInfoDAO deviceUserInfoDAO;
    @Autowired
    private  ConstructionSiteDischargeDAO constructionSiteDischargeDAO;
    @Autowired
    private  ConstructionSiteTowerDAO constructionSiteTowerDAO;
    @Autowired
    private  ConstructionSiteDustDAO constructionSiteDustDAO;
    @Autowired
    private  ConstructionEquipmentService constructionEquipmentService;
    @Autowired
    private DustThresholdDAO dustThresholdDAO;
    @Autowired
    private IntelligentSprayService intelligentSprayService;
    @Autowired
    private SprayService sprayService;

    @Override
    public String pushData(ThirdApiVO thirdApiVO) {
        log.info("推送数据:{}", thirdApiVO);
        checkToken(thirdApiVO);
        String data = thirdApiVO.getData();
        try {
            if (PushEquipmentEnum.DUST.getDeviceType().equals(thirdApiVO.getDeviceType())) {
                //扬尘
                List<ConstructionSiteDustDO> constructionSiteDustDOList = JacksonTool.parseToObject(data, new TypeReference<List<ConstructionSiteDustDO>>() {});
                check(constructionSiteDustDOList,PushEquipmentEnum.DUST.getDeviceType());

            } else if (PushEquipmentEnum.TOWER.getDeviceType().equals(thirdApiVO.getDeviceType())) {
                //塔机设备
                List<ConstructionSiteTowerDO> constructionSiteDischargeDOList = JacksonTool.parseToObject(data, new TypeReference<List<ConstructionSiteTowerDO>>() {});
                check(constructionSiteDischargeDOList,PushEquipmentEnum.TOWER.getDeviceType());
            } else if (PushEquipmentEnum.DISCHARGE.getDeviceType().equals(thirdApiVO.getDeviceType())) {
                //卸料设备
                List<ConstructionSiteDischargeDO> constructionSiteDischargeDOS = JacksonTool.parseToObject(data, new TypeReference<List<ConstructionSiteDischargeDO>>() {});
                check(constructionSiteDischargeDOS,PushEquipmentEnum.DISCHARGE.getDeviceType());
            }else{
                log.info("设备类型不正确");
                throw new ConstructionBizException(BizCodeMsgEnum.PUSH_DEVICE_TYPE_ERROR);
            }
        } catch (ConstructionBizException constructionBizException) {
            throw  constructionBizException;
        } catch (Exception e) {
            log.info("推送数据格式不正确");
            throw new ConstructionBizException(BizCodeMsgEnum.PUSH_DATA_FORMAT_ERROR);
        }
        return "";
    }

    /**
     * token校验
     * @param thirdApiVO
     * @return
     */
    private void checkToken(ThirdApiVO thirdApiVO) {
        //校验token
        DeviceUserInfoDO deviceUserInfoDO = deviceUserInfoDAO.selectOne(new EntityWrapper<DeviceUserInfoDO>().eq(DeviceUserInfoDOPropertiesEnum.USER_NAME.getColumn(), thirdApiVO.getUserName()));
        if (deviceUserInfoDO == null) {
            log.info("用户不存在");
            throw new ConstructionBizException(BizCodeMsgEnum.PUSH_TOKEN_CHECK_FAIL);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String plainText = thirdApiVO.getUserName() + deviceUserInfoDO.getPassword() + sdf.format(new Date());
        if (!thirdApiVO.getToken().equals(MD5Tool.md5(plainText))) {
            log.info("token校验失败");
            throw new ConstructionBizException(BizCodeMsgEnum.PUSH_TOKEN_CHECK_FAIL);
        }
    }

    /**
     * 检验数据
     * @param list
     * @param deviceType
     */
    public void check(List list, String deviceType){
        List<String> errorList = new ArrayList<>();
        for (Object object: list) {
            String checkResult = ValidatorTool.validateThrowError(object);
            if (StringUtils.isEmpty(checkResult)) {
                switch (deviceType) {
                    case "1":addDustData((ConstructionSiteDustDO)object); break;
                    case "2": addTowerData((ConstructionSiteTowerDO)object); break;
                    case "3": addDischargeData((ConstructionSiteDischargeDO)object);break;
                    default: break;
                }
            } else {
                errorList.add(checkResult);
            }
        }
        if(errorList.size()>0) {
            throw new ConstructionBizException(BizCodeMsgEnum.PUSH_DATA_FORMAT_ERROR.getCode(), errorList.get(0), ValidatorTool.class);
        }
    }

    /**
     * 添加塔机数据
     * @param constructionSiteTowerDO
     * @return
     */
    public  void addTowerData(ConstructionSiteTowerDO constructionSiteTowerDO) {
        constructionSiteTowerDO.setGmtCreate(new Date());
        constructionSiteTowerDAO.insert(constructionSiteTowerDO);
    }

    /**
     * 添加扬尘数据
     * @param constructionSiteDustDO
     * @return
     */
    public void addDustData(ConstructionSiteDustDO constructionSiteDustDO) {
        ConstructionSiteDustDO constructionSiteDustDO1 = new ConstructionSiteDustDO();
        ConstructionEquipmentDTO equipment = constructionEquipmentService.getProjectNoByEquipmentNo(constructionSiteDustDO.getDeviceNo());
        if (equipment != null && equipment.getProjectNo() != null) {
            constructionSiteDustDO1.setProjectNo(equipment.getProjectNo());
        }
        constructionSiteDustDO1.setDeviceNo(constructionSiteDustDO.getDeviceNo())
                .setTemperature(constructionSiteDustDO.getTemperature() + "℃")
                .setHumidity(constructionSiteDustDO.getHumidity() + "%RH")
                .setAirPressure(constructionSiteDustDO.getAirPressure() + "Kpa")
                .setWindSpeed(constructionSiteDustDO.getWindSpeed() + "m/s")
                .setDirectionWind(constructionSiteDustDO.getDirectionWind())
                .setParticulate25(constructionSiteDustDO.getParticulate25() + "ug/m3")
                .setParticulate10(constructionSiteDustDO.getParticulate10() + "ug/m3")
                .setSuspendedParticles(constructionSiteDustDO.getSuspendedParticles() + "ug/m3")
                .setNoise(constructionSiteDustDO.getNoise() + "dB")
                .setTransmissionTime(constructionSiteDustDO.getTransmissionTime())
                .setGmtCreate(new Date());
        constructionSiteDustDAO.insert(constructionSiteDustDO1);
        checkDust(constructionSiteDustDO1);
        checkDustAndSendMessage(constructionSiteDustDO1);
    }

    /**
     * 添加卸料平台数据
     * @param constructionSiteDischargeDO
     * @return
     */
    private void addDischargeData(ConstructionSiteDischargeDO constructionSiteDischargeDO) {
        ConstructionSiteDischargeDO constructionSiteDischarge = new ConstructionSiteDischargeDO();
        BeanUtils.copyProperties(constructionSiteDischargeDO, constructionSiteDischarge);
        constructionSiteDischarge.setGmtCreate(new Date());
        constructionSiteDischargeDAO.insert(constructionSiteDischarge);
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
}
