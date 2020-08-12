package com.wisdomconstruction.wisdomConstruction.service.intelligentspray;

import com.wisdomconstruction.wisdomConstruction.common.dto.intelligentspray.IntelligentSprayDTO;

/**
 * 智能喷淋业务层接口
 * @author wangyongfei
 * @date 2020/7/4 10:23
 */
public interface IntelligentSprayService {

    /**
     * 获取开关状态
     * @param projectNo 项目编号
     * @return
     */
    IntelligentSprayDTO getSwitchStatus(String projectNo);

    /**
     * 改变开关状态
     * @param projectNo
     * @param status
     * @return
     */
    boolean changeMainSwitch(String projectNo, boolean status);

    /**
     * 改变喷淋开关状态
     * @param projectNo
     * @param status
     * @return
     */
    boolean changeSpraySwitch(String projectNo, boolean status);
}