package com.wisdomconstruction.wisdomConstruction.common.dto.dustthreshold;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 扬尘阈值DTO
 * @author wangyongfei
 * @date 2020/6/28 20:44
 */
@Data
public class DustThresholdDTO {

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
     * 温度阈值
     */
    private Float temperatureThreshold;

    /**
     * 湿度阈值
     */
    private Float humidityThreshold;

    /**
     * 气压阈值
     */
    private Float airPressureThreshold;

    /**
     * 风速阈值
     */
    private Float windSpeedThreshold;

    /**
     * PM2.5阈值
     */
    @Min(value = 0,message = "输入数据有误")
    private Float particulate25Threshold;

    /**
     * PM10阈值
     */
    @Min(value = 0,message = "输入数据有误")
    private Float particulate10Threshold;

    /**
     * 悬浮颗粒阈值
     */
    private Float suspendedParticlesThreshold;

    /**
     * 噪音阈值
     */
    private Float noiseThreshold;
}