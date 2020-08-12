package com.wisdomconstruction.wisdomConstruction.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.enums.DustGetDataMethodEnum;
import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import com.wisdomconstruction.wisdomConstruction.tool.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import static com.wisdomconstruction.wisdomConstruction.service.impl.DmodelServiceImpl.analysisData;

@Component
@Slf4j
public class Schedule {
    @Autowired
    private ConstructionSiteDustDAO constructionSiteDustDAO;
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;
    @Autowired
    private DustThresholdDAO dustThresholdDAO;
    @Autowired
    private SprayService sprayService;

    /**
     *  扬尘定时任务
     *  每一小时执行一次
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void printDate(){
        log.info("定时任务："+new Date().toLocaleString());
        String hexString = "01 03 01 F4 00 0B 44 03";
        log.info("定时任务扬尘请求参数:" + hexString);
        //根据项目编号查询设备列表
        EntityWrapper<ConstructionEquipmentDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), EquipmentEnum.DUST.getDeviceType())
                .eq(ConstructionEquipmentDOPropertiesEnum.GET_DATA_METHOD.getColumn(), DustGetDataMethodEnum.ACTIVE_ACCESS.getMethodNo());
        List<ConstructionEquipmentDO> collect = constructionEquipmentDAO.selectList(entityWrapper);
        if (collect != null) {
            collect.forEach(constructionEquipmentDO -> {
                String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(), hexString);
                if (!StringUtils.isEmpty(resultString)) {
                    ConstructionSiteDustDO constructionSiteDustDO = analysisData(resultString);
                    if (constructionSiteDustDO != null) {
                        constructionSiteDustDO.setProjectNo(constructionEquipmentDO.getProjectNo());
                        constructionEquipmentDO.setCreatorInfo();
                        constructionEquipmentDO.setModifierInfo();
                        constructionEquipmentDO.setGmtCreate(new Date());
                        constructionEquipmentDO.setDtuImei(constructionEquipmentDO.getDtuImei());
                        checkDust(constructionSiteDustDO);
                        boolean insert = constructionSiteDustDAO.insert(constructionSiteDustDO);
                        if (!insert) {
                            log.error("插入数据库失败");
                        }
                    }
                }
            });
        }
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

}
