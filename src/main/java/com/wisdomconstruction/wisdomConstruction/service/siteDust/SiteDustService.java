package com.wisdomconstruction.wisdomConstruction.service.siteDust;

import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;

import java.util.List;
import java.util.Map;

/**
 * @author kongke
 * @version 1.0
 * @intention
 * @date 2020/5/7 14:21
 */
public interface SiteDustService {

    /**
     * 列出空气质量
     * @param projectNo 项目编号
     * @param isInWeek 是否为一周内，1代表是
     * @return
     */
    Map<?, ?> listAirQuality(String projectNo, Integer isInWeek);

    /**
     * 扬尘设备实时数据
     *
     * @param modelType 设备类型
     * @param programNo 项目编号
     * @return
     */
    List<ConstructionSiteDustDTO> dust(String modelType, String programNo);

    /**
     * AT指令控制DTU
     *
     * @param at       指令
     * @param dtuImei  设备imei
     * @return String
     */
     String atInstruction(String at, String dtuImei);

    /**
     * 近七天的扬尘数据展示
     * @param constructionSiteDustDO
     * @return
     */
     ConstructionSiteDustDTO renderDust(ConstructionSiteDustDO constructionSiteDustDO);

}
