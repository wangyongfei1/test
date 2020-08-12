package com.wisdomconstruction.wisdomConstruction.common.dto.dust;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * @author kongke
 * @version 1.0
 * @intention
 * @date 2020/5/7 13:11
 */
@Data
@Accessors(chain = true)
public class ConstructionSiteDustDTO {

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 设备编号
     */
    private String deviceNo;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 湿度
     */
    private String humidity;

    /**
     * 气压
     */
    private String airPressure;

    /**
     * 风速
     */
    private String windSpeed;

    /**
     * 风向
     */
    private String directionWind;

    /**
     * PM2.5
     */
    private String particulate25;

    /**
     * PM10
     */
    private String particulate10;

    /**
     * 悬浮颗粒(TSP)
     */
    private String suspendedParticles;

    /**
     * 噪音
     */
    private String noise;

    /**
     * 数据采集时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date transmissionTime;

    /**
     * 24小时内的温度
     */
    private Map<String, Float> temperatureInWeek;
    /**
     * 24小时内的湿度
     */
    private Map<String, Float> humidityInWeek;
    /**
     * 24小时内的噪音
     */
    private Map<String, Float> noiseInWeek;
    /**
     * 24小时内的风速
     */
    private Map<String, Float> windSpeedInWeek;
    /**
     * 24小时内的PM2.5
     */
    private Map<String, Float> pm25InWeek;
    /**
     * 24小时内的PM10
     */
    private Map<String, Float> pm10InWeek;
}
