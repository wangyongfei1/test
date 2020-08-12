package com.wisdomconstruction.wisdomConstruction.service;

import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;

import java.util.List;

public interface DmodelService {

    /**
     * 扬尘设备实时数据
     */
    List<ConstructionSiteDustDO> dust(String modelType, String programNo);

    /**
     * 塔机设备实时数据
     */
    void tower();

    /**
     * 施工升降机实时数据
     */
    void elevator();

    /**
     * 施工升降机实时数据
     */
    void electrical_fire();



    /**
     * 获取DTU的SN码
     */
    //String acquire();


}
