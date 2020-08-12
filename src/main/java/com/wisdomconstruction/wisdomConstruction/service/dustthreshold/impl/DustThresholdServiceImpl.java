package com.wisdomconstruction.wisdomConstruction.service.dustthreshold.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.dustthreshold.DustThresholdDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold.DustThresholdDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.dustthreshold.DustThresholdDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.siteDust.ConstructionSiteDustDAO;
import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.enums.DustGetDataMethodEnum;
import com.wisdomconstruction.wisdomConstruction.service.dustthreshold.DustThresholdService;
import com.wisdomconstruction.wisdomConstruction.service.intelligentspray.IntelligentSprayService;
import com.wisdomconstruction.wisdomConstruction.service.siteDust.SiteDustService;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.tool.websocket.WebSocketServer;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author wangyongfei
 * @date 2020/6/29 20:36
 */
@Service
public class DustThresholdServiceImpl implements DustThresholdService {

    @Autowired
    private DustThresholdDAO dustThresholdDAO;
    @Autowired
    private SiteDustService siteDustService;
    @Autowired
    private ConstructionSiteDustDAO constructionSiteDustDAO;
    @Autowired
    private SprayService sprayService;
    @Autowired
    private ConstructionEquipmentDAO constructionEquipmentDAO;

    /**
     * 更新扬尘阈值
     * @param dustThresholdDTO
     * @return
     */
    @Override
    public boolean updateDustThreshold(DustThresholdDTO dustThresholdDTO) {
        String projectNo = dustThresholdDTO.getProjectNo();
        DustThresholdDO dustThreshold = dustThresholdDAO.getDustThreshold(projectNo);
        DustThresholdDO dustThresholdDO = new DustThresholdDO();
        BeanUtils.copyProperties(dustThresholdDTO,dustThresholdDO);
        boolean result = dustThresholdDAO.updateDustThreshold(dustThresholdDO);
        if(result){
            checkDust(dustThresholdDTO,dustThreshold);
        }
        return result;
    }

    /**
     * 获取扬尘阈值
     * @param projectNo
     * @return
     */
    @Override
    public DustThresholdDTO getDustThreshold(String projectNo) {
        DustThresholdDTO dustThresholdDTO = new DustThresholdDTO();
        DustThresholdDO dustThresholdDO = dustThresholdDAO.getDustThreshold(projectNo);
        if(dustThresholdDO !=null){
            BeanUtils.copyProperties(dustThresholdDO,dustThresholdDTO);
            return dustThresholdDTO;
        }
        return dustThresholdDTO;
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
     * 校验扬尘数据
     * @param dustThresholdDTO
     * @param dustThreshold
     */
    private synchronized void checkDust(DustThresholdDTO dustThresholdDTO,DustThresholdDO dustThreshold) {
        try {
            String projectNo = dustThresholdDTO.getProjectNo();
            List<ConstructionEquipmentDTO> equipmentList = constructionEquipmentDAO.getEquipment(EquipmentEnum.DUST.getDeviceType(), projectNo);
            if(CollectionUtil.isNotEmpty(equipmentList)) {
                if (ObjectUtils.isNotEmpty(dustThreshold) && dustThreshold.getPowerSwitch() == 1) {
                    if (equipmentList.get(0).getGetDataMethod().equals(DustGetDataMethodEnum.ACTIVE_ACCESS.getMethodNo()) ) {
                        siteDustService.dust(EquipmentEnum.DUST.getDeviceType(), projectNo);
                    } else if (equipmentList.get(0).getGetDataMethod().equals(DustGetDataMethodEnum.AUTOMATIC_RECEPTION.getMethodNo())) {
                        ConstructionSiteDustDO constructionSiteDust = constructionSiteDustDAO.getConstructionSiteDust(projectNo);
                        //校验扬尘数据是否超过阈值
                        boolean particulate25 = false;
                        boolean particulate10 = false;
                        if (ObjectUtils.isNotEmpty(dustThresholdDTO.getParticulate25Threshold())) {
                            particulate25 = isGreaterThreshold(constructionSiteDust.getParticulate25(),
                                    dustThresholdDTO.getParticulate25Threshold());
                            if (!particulate25) {
                                particulate10 = isGreaterThreshold(constructionSiteDust.getParticulate10(),
                                        dustThreshold.getParticulate10Threshold());
                            }
                        }
                        if (ObjectUtils.isNotEmpty(dustThresholdDTO.getParticulate10Threshold())) {
                            particulate10 = isGreaterThreshold(constructionSiteDust.getParticulate10(),
                                    dustThresholdDTO.getParticulate10Threshold());
                            if (!particulate10) {
                                particulate25 = isGreaterThreshold(constructionSiteDust.getParticulate25(),
                                        dustThreshold.getParticulate25Threshold());
                            }
                        }
                        if (particulate25 || particulate10) {
                            boolean result = sprayService.openTheValve(projectNo, 1);
                            if (result) {
                                boolean isUpdate = updateSwitchStatus(projectNo, 1);
                                if (isUpdate) {
                                    sendMessage("1", projectNo);
                                }
                            }
                        } else {
                            if (dustThreshold.getSwitchStatus() == 0) {
                                sendMessage("0", projectNo);
                            } else {
                                boolean result = sprayService.openTheValve(projectNo, 0);
                                if (result) {
                                    boolean isUpdate = updateSwitchStatus(projectNo, 0);
                                    if (isUpdate) {
                                        sendMessage("0", projectNo);
                                    }
                                }
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
}