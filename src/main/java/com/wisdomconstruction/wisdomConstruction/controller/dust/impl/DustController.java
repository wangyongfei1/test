package com.wisdomconstruction.wisdomConstruction.controller.dust.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import com.wisdomconstruction.wisdomConstruction.controller.dust.DustApi;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust.ConstructionSiteDustDO;
import com.wisdomconstruction.wisdomConstruction.service.siteDust.SiteDustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DustController implements DustApi {
    @Autowired
    private SiteDustService dustService;
    /**
     * 扬尘设备实时数据
     * @param  modelType  设备类型
     * @param  programNo  项目编号
     * @return
     */
    public List<ConstructionSiteDustDTO> dust(@RequestParam String modelType, @RequestParam String programNo) {
        return dustService.dust(modelType, programNo);
    }

    /**
     * 近七天的空气质量
     * @param projectNo
     * @return
     */
    public Map<?, ?> listAirQuality(@RequestParam String projectNo){
        return dustService.listAirQuality(projectNo,1);
    }

    /**
     * 近七天的扬尘数据展示
     * @param projectNo
     * @return
     */
    @Override
    public ConstructionSiteDustDTO renderDust(String projectNo) {
        ConstructionSiteDustDO constructionSiteDustDO = new ConstructionSiteDustDO();
        constructionSiteDustDO.setProjectNo(projectNo);
        return dustService.renderDust(constructionSiteDustDO);
    }
}