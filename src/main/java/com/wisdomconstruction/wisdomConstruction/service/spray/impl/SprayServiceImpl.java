package com.wisdomconstruction.wisdomConstruction.service.spray.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment.ConstructionEquipmentDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment.ConstructionEquipmentDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.equipment.ConstructionEquipmentDAO;
import com.wisdomconstruction.wisdomConstruction.enums.EquipmentEnum;
import com.wisdomconstruction.wisdomConstruction.enums.ValveStatusEnum;
import com.wisdomconstruction.wisdomConstruction.service.spray.SprayService;
import com.wisdomconstruction.wisdomConstruction.tool.ServiceSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 喷淋
 */
@Service
@Slf4j
public class SprayServiceImpl implements SprayService {


    @Autowired
    private ConstructionEquipmentDAO  constructionEquipmentDAO;

    /**
     * 开阀/关阀
     *
     * @param programNo 项目编号
     * @param status
     * @return
     */
    @Override
    public boolean openTheValve(String programNo,Integer status) {
      //获取设备号
        ConstructionEquipmentDO constructionEquipmentDO = constructionEquipmentDAO.selectOne(new EntityWrapper<ConstructionEquipmentDO>().eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), programNo)
                .eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), Integer.valueOf(EquipmentEnum.SPRAY.getDeviceType())));
        if (constructionEquipmentDO!=null){
            String hexString="";
            if (ValveStatusEnum.CLOSE.getCode().equals(status)){
                //关阀发送指令01 05 00 01 FF 00 DD FA
                 hexString="01 05 00 01 FF 00 DD FA";
            }else if (ValveStatusEnum.OPEN.getCode().equals(status)){
                //开阀发送指令01 05 00 02 FF 00 2D FA
                 hexString="01 05 00 02 FF 00 2D FA";
            }else{
                log.info("参数不正确,status{}",status);
                return false;
            }
            String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(),hexString);
            if (StringUtils.isBlank(resultString)){
                log.info("喷淋设备未连接,项目编号{}",programNo);
            }else{
                resultString = resultString.replace(" ", "").substring(0,16);
                hexString = hexString.replace(" ", "");
                if (hexString.equalsIgnoreCase(resultString)){
                    return true;
                }else{
                    log.info("指令相应错误,错误回复指令{}",hexString);
                }
            }

        }
        return false;
    }


    /**
     * 获取状态
     * @return
     */
    @Override
    public Integer getValveStatus(String programNo) {
        //获取设备号
        ConstructionEquipmentDO constructionEquipmentDO = constructionEquipmentDAO.selectOne(new EntityWrapper<ConstructionEquipmentDO>().eq(ConstructionEquipmentDOPropertiesEnum.PROJECT_NO.getColumn(), programNo)
                .eq(ConstructionEquipmentDOPropertiesEnum.DEVICE_TYPE.getColumn(), Integer.valueOf(EquipmentEnum.SPRAY.getDeviceType())));
        if (constructionEquipmentDO!=null){
            String hexString="01 01 00 02 00 01 5C 0A";
            String resultString = new ServiceSocket().send(constructionEquipmentDO.getDtuImei(),hexString);
            if (StringUtils.isBlank(resultString)){
                log.info("喷淋设备未连接,项目编号{}", hexString);
            } else {
                return analysisData(resultString);
            }
        }
        return null;
    }

    /**
     * 解析喷淋状态
     * @return
     */
    private Integer analysisData(String resultString){
        try {
            log.info("获取喷淋状态返回数据:{}", resultString);
            resultString = resultString.replace(" ", "");
            //净重
            String substring = resultString.substring(6, 8);
            return Integer.parseInt(substring);
        } catch (Exception e) {
            log.info("获取喷淋状态解析失败,原因{}",e.getMessage());
        }
        return null;
    }
}
