package com.wisdomconstruction.wisdomConstruction.service.intelligentspray.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.intelligentspray.IntelligentSprayDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.enums.DustGetDataMethodEnum;
import com.wisdomconstruction.wisdomConstruction.service.intelligentspray.IntelligentSprayService;
import com.wisdomconstruction.wisdomConstruction.service.siteDust.SiteDustService;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.tool.websocket.WebSocketServer;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 智能喷淋业务层实现类
 * @author wangyongfei
 * @date 2020/7/4 10:23
 */
@Service
public class IntelligentSprayServiceImpl implements IntelligentSprayService {

    @Autowired
    private DustThresholdDAO dustThresholdDAO;
    @Autowired
    private SiteDustService siteDustService;
    @Autowired
    private SprayService sprayService;
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;
    /**
     * 获取开关状态
     * @param projectNo 项目编号
     * @return
     */
    @Override
    public IntelligentSprayDTO getSwitchStatus(String projectNo) {
        IntelligentSprayDTO intelligentSprayDTO = new IntelligentSprayDTO();
        List<ConstructionEquipmentDTO> equipmentList = constructionEquipmentDAO.getEquipment(EquipmentEnum.DUST.getDeviceType(), projectNo);
        if(CollectionUtil.isNotEmpty(equipmentList)) {
            //获取总开关状态
            DustThresholdDO dustThreshold = dustThresholdDAO.getDustThreshold(projectNo);
            if (ObjectUtils.isNotEmpty(dustThreshold) && dustThreshold.getPowerSwitch() == 1) {
                intelligentSprayDTO.setMainSwitchStatus(true);
                return intelligentSprayDTO;
            } else {
                intelligentSprayDTO.setMainSwitchStatus(false);
                return intelligentSprayDTO;
            }
        } else {
            return intelligentSprayDTO;
        }
    }

    /**
     * 改变开关状态
     * @param projectNo 项目编号
     * @param status 开关状态
     * @return
     */
    @Override
    public boolean changeMainSwitch(String projectNo, boolean status) {
        DustThresholdDO dustThresholdDO = new DustThresholdDO();
        dustThresholdDO.setProjectNo(projectNo);
        if(!status){
            dustThresholdDO.setPowerSwitch(0);
            boolean result = sprayService.openTheValve(projectNo, 0);
            if(result){
                dustThresholdDO.setSwitchStatus(0);
                boolean isUpdate = dustThresholdDAO.updateDustThreshold(dustThresholdDO);
                if(isUpdate){
                    sendMessage("main,false", projectNo);
                    sendMessage("0", projectNo);
                    return true;
                }else {
                    return false;
                }
            }
        }else if(status){
            dustThresholdDO.setPowerSwitch(1);
            boolean result = dustThresholdDAO.updateDustThreshold(dustThresholdDO);
            if(result){
                sendMessage("main,true", projectNo);
                siteDustService.dust(EquipmentEnum.DUST.getDeviceType(),projectNo);
                return result;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 改变喷淋开关状态
     * @param projectNo
     * @param status
     * @return
     */
    @Override
    public boolean changeSpraySwitch(String projectNo, boolean status) {
        DustThresholdDO dustThresholdDO = new DustThresholdDO();
        dustThresholdDO.setProjectNo(projectNo);
        if(!status){
            return updateAndSend(dustThresholdDO,0);
        }else if(status){
            DustThresholdDO dustThreshold = dustThresholdDAO.getDustThreshold(projectNo);
            if (ObjectUtils.isNotEmpty(dustThreshold) && dustThreshold.getPowerSwitch() == 1) {
                return updateAndSend(dustThresholdDO,1);
            }
            return false;
        }
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
     * 更新开关状态并发送指令
     * @param dustThresholdDO
     * @param status
     * @return
     */
    private boolean updateAndSend(DustThresholdDO dustThresholdDO,Integer status){
        dustThresholdDO.setSwitchStatus(status);
        boolean result = sprayService.openTheValve(dustThresholdDO.getProjectNo(), status);
        if(result){
            sendMessage(String.valueOf(status), dustThresholdDO.getProjectNo());
        }
        boolean isUpdate = dustThresholdDAO.updateDustThreshold(dustThresholdDO);
        if(result && isUpdate){
            return true;
        }else {
            return false;
        }
    }

}