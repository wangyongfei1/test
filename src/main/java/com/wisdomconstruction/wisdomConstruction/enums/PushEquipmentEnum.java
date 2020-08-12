package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.Getter;

@Getter
public enum PushEquipmentEnum {

    DUST("1", "扬尘设备"),
    TOWER("2", "塔机设备"),
    DISCHARGE("3", "卸料平台"),

    ;

    private String deviceType;
    private String deviceName;

    PushEquipmentEnum(String deviceType, String deviceName) {
        this.deviceType =deviceType;
        this.deviceName =deviceName;
    }
}
