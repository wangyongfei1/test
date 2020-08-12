package com.wisdomconstruction.wisdomConstruction.controller.intelligentspray.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.intelligentspray.IntelligentSprayDTO;
import com.wisdomconstruction.wisdomConstruction.controller.intelligentspray.IntelligentSprayApi;
import com.wisdomconstruction.wisdomConstruction.service.intelligentspray.IntelligentSprayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 智能喷淋控制层
 * @author wangyongfei
 * @date 2020/7/4 10:20
 */
@RestController
public class IntelligentSprayController implements IntelligentSprayApi {

    @Autowired
    private IntelligentSprayService intelligentSprayService;
    /**
     * 获取开关状态
     * @param projectNo 项目编号
     * @return
     */
    @Override
    public IntelligentSprayDTO getSwitchStatus(@RequestParam String projectNo) {
        return intelligentSprayService.getSwitchStatus(projectNo);
    }

    /**
     * 改变总开关状态
     * @param projectNo 项目编号
     * @param status
     * @return
     */
    @Override
    public boolean changeMainSwitch(String projectNo,boolean status) {
        return intelligentSprayService.changeMainSwitch(projectNo,status);
    }


    /**
     * 改变喷淋开关状态
     * @param projectNo 项目编号
     * @param status
     * @return
     */
    @Override
    public boolean changeSpraySwitch(String projectNo,boolean status) {
        return intelligentSprayService.changeSpraySwitch(projectNo,status);

    }
}