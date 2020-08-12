package com.wisdomconstruction.wisdomConstruction.dao.base.enums.siteDust;

/**
 * <p>
 * 扬尘设备表属性列名枚举
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public enum ConstructionSiteDustDOPropertiesEnum {

    /**
     * 项目编号
     */
    PROJECT_NO("projectNo", "project_no"),
    /**
     * 设备编号
     */
    DEVICE_NO("deviceNo", "device_no"),
    /**
     * 温度
     */
    TEMPERATURE("temperature", "temperature"),
    /**
     * 湿度
     */
    HUMIDITY("humidity", "humidity"),
    /**
     * 气压
     */
    AIR_PRESSURE("airPressure", "air_pressure"),
    /**
     * 风速
     */
    WIND_SPEED("windSpeed", "wind_speed"),
    /**
     * 风向
     */
    DIRECTION_WIND("directionWind", "direction_wind"),
    /**
     * PM2.5
     */
    PARTICULATE_25("particulate25", "particulate_25"),
    /**
     * PM10
     */
    PARTICULATE_10("particulate10", "particulate_10"),
    /**
     * 悬浮颗粒(TSP)
     */
    SUSPENDED_PARTICLES("suspendedParticles", "suspended_particles"),
    /**
     * 噪音
     */
    NOISE("noise", "noise"),

    /**
     * 数据采集时间
     */
    TRANSMISSION_TIME("transmissionTime","transmission_time"),
    /**
     * 数据生成时间
     */
    GMT_CREATE("gmtCreate", "gmt_create")
    ;

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    ConstructionSiteDustDOPropertiesEnum(String property, String column) {
        this.property = property;
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }
}
