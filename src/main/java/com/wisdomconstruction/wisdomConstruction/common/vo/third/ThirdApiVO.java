package com.wisdomconstruction.wisdomConstruction.common.vo.third;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ThirdApiVO {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String userName;

    /**
     * 设备类型
     */
    @NotBlank(message = "设备类型不能为空")
    private String deviceType;
    /**
     * token
     */
    @NotBlank(message = "token不能为空")
    private String token;
    /**
     * 数据
     */
    @NotBlank(message = "数据不能为空")
    private String data;
}