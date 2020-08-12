package com.wisdomconstruction.wisdomConstruction.dao.base.domain.dustthreshold;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 扬尘阈值实体类
 * @author wangyongfei
 * @date 2020/6/28 20:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_site_dust_threshold")
public class DustThresholdDO extends BaseBizModel {

    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 设备编号
     */
    @TableField("device_no")
    private String deviceNo;

    /**
     * 温度阈值
     */
    @TableField("temperature_threshold")
    private Float temperatureThreshold;

    /**
     * 湿度阈值
     */
    @TableField("humidity_threshold")
    private Float humidityThreshold;

    /**
     * 气压阈值
     */
    @TableField("air_pressure_threshold")
    private Float airPressureThreshold;

    /**
     * 风速阈值
     */
    @TableField("wind_speed_threshold")
    private Float windSpeedThreshold;

    /**
     * PM2.5阈值
     */
    @TableField("particulate_25_threshold")
    private Float particulate25Threshold;

    /**
     * PM10阈值
     */
    @TableField("particulate_10_threshold")
    private Float particulate10Threshold;

    /**
     * 悬浮颗粒阈值
     */
    @TableField("suspended_particles_threshold")
    private Float suspendedParticlesThreshold;

    /**
     * 噪音阈值
     */
    @TableField("noise_threshold")
    private Float noiseThreshold;

    /**
     * 开关状态 0-关 1-开
     */
    @TableField("switch_status")
    private Integer switchStatus;

    /**
     * 模拟电源开关 0-关   1-开
     */
    @TableField("power_switch")
    private Integer powerSwitch;


}