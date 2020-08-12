package com.wisdomconstruction.wisdomConstruction.dao.base.enums.equipment;

import com.baomidou.mybatisplus.annotations.TableField;

/**
 * <p>
 * 设备表属性列名枚举
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public enum ConstructionEquipmentDOPropertiesEnum {

    /**
     * 项目编号
     */
    PROJECT_NO("projectNo", "project_no"),
    /**
     * 设备名称
     */
    DEVICE_NAME("deviceName", "device_name"),
    /**
     * 设备类型 0：扬尘 1：塔机 2：施工升降机 3：深基坑 4：卸料台 5：高支模 6：智能电表 7：智能水表
     */
    DEVICE_TYPE("deviceType", "device_type"),
    /**
     * 是否连接DTU 0：有 1：否
     */
    HAS_DTU("hasDtu", "has_dtu"),
    /**
     * 获取数据方式 1-主动获取 2-自动接收
     */
    GET_DATA_METHOD("getDataMethod", "get_data_method"),
    /**
     * dtu唯一标识码imei
     */
    DTU_IMEI("dtuImei", "dtu_imei"),
    /**
     * 设备状态 0-在线（正常） 1-离线（异常）
     */
    STATUS("status","status"),
    /**
     * 备注
     */
    REMARK("remark", "remark");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    ConstructionEquipmentDOPropertiesEnum(String property, String column) {
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
