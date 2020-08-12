package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.Getter;

@Getter
public enum EquipmentEnum {

    DUST("0", "扬尘设备"),
    TOWER("1", "塔机设备"),
    ELEVATOR("2", "施工升降机"),
    DEEP_EXCAVATION("3", "深基坑"),
    LOADING("4", "料台"),
    MONITORING("5", "高支模监控"),
    SMART_METERS("6", "智能电表"),
    WEIGHT("8", "地磅"),
    SPRAY("9", "喷淋"),
    ;

    private String deviceType;
    private String deviceName;

    EquipmentEnum(String deviceType, String deviceName) {
        this.deviceType =deviceType;
        this.deviceName =deviceName;
    }
}
