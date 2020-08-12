package com.wisdomconstruction.wisdomConstruction.controller.dustthreshold.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.dustthreshold.DustThresholdDTO;
import com.wisdomconstruction.wisdomConstruction.controller.dustthreshold.DustThresholdApi;
import com.wisdomconstruction.wisdomConstruction.service.dustthreshold.DustThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


/**
 * @author wangyongfei
 * @date 2020/6/29 20:32
 */
@RestController
public class DustThresholdController implements DustThresholdApi {

    @Autowired
    private DustThresholdService dustThresholdService;

    @Override
    public boolean updateDustThreshold(@RequestBody DustThresholdDTO dustThresholdDTO) {
        return dustThresholdService.updateDustThreshold(dustThresholdDTO);
    }

    @Override
    public DustThresholdDTO getDustThreshold(@RequestParam String projectNo) {
        return dustThresholdService.getDustThreshold(projectNo);
    }
}