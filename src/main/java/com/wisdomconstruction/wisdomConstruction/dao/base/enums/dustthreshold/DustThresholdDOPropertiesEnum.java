package com.wisdomconstruction.wisdomConstruction.dao.base.enums.dustthreshold;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangyongfei
 * @date 2020/6/28 20:46
 */
@Getter
@AllArgsConstructor
public enum  DustThresholdDOPropertiesEnum {
    /**
     * 项目编号
     */
    PROJECT_NO("project_no","projectNo"),

    /**
     * 设备编号
     */
    DEVICE_NO("device_no","deviceNo"),
    /**
     * 温度阈值
     */
    TEMPERATURE_THRESHOLD("temperature_threshold","temperatureThreshold"),

    /**
     * 湿度阈值
     */
    HUMIDITY_THRESHOLD("humidity_threshold","humidityThreshold"),
    /**
     * 气压阈值
     */
    AIR_PRESSURE_THRESHOLD("air_pressure_threshold","airPressureThreshold"),
    /**
     * 风速阈值
     */
    WIND_SPEED_THRESHOLD("wind_speed_threshold","windSpeedThreshold"),
    /**
     * PM2.5阈值
     */
    PARTICULATE_25_THRESHOLD("particulate_25_threshold","particulate25Threshold"),
    /**
     * PM10阈值
     */
    PARTICULATE_10_THRESHOLD("particulate_10_threshold","particulate10Threshold"),
    /**
     * 悬浮颗粒阈值
     */
    SUSPENDED_PARTICLES_THRESHOLD("suspended_particles_threshold","suspendedParticlesThreshold"),
    /**
     * 噪音阈值
     */
    NOISE_THRESHOLD("noise_threshold","noiseThreshold"),
    /**
     * 开关状态
     */
    SWITCH_STATUS("switch_status","switchStatus"),
    /**
     * 模拟电源开关
     */
    POWER_SWITCH("power_switch","powerSwitch");

    /**
     * 表字段名
     */
    private String column;

    /**
     * 属性名
     */
    private String property;

}