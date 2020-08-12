package com.wisdomconstruction.wisdomConstruction.common.dto.intelligentspray;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 智能喷淋开关 DTO
 * @author wangyongfei
 * @date 2020/7/4 10:09
 */
@Data
public class IntelligentSprayDTO {

    /**
     * 项目编号
     */
    @NotBlank(message = "项目编号不能为空")
    private String projectNo;
    /**
     * 设备编号
     */
    private String deviceNo;
    /**
     * 智能喷淋开关
     */
    private Boolean spraySwitchStatus;
    /**
     * 总开关
     */
    private Boolean mainSwitchStatus;
}