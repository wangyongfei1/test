package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.Getter;

@Getter
public enum DmodelEnum {


    DUST(0, "扬尘设备"),
    TOWER(1, "塔机设备"),
    ELEVATOR(2, "施工升降机"),
    LOADING(3, "料台"),
    BASKET(4, "吊篮"),
    ELECTRICAL_FIRE(5, "电气火灾"),
    MONITORING(6, "高支模监控"),
    SWITCHING_CONTROL(7, "远程开关控制"),
    DUST_CONTROL_UNIT(8, "除尘控制器"),
    CURING_ROOM(9, "养护室"),
    LINKAGE_CONTROLLER(10, "联动控制器"),
    SMART_METERS(11, "智能电表"),
    ONLINE_TEMPERATURE_MONITORING(12, "在线体温监测");

    private Integer DModel_code;
    private String DModel_Name;

    DmodelEnum(Integer DModel_code, String DModel_Name) {
        this.DModel_code =DModel_code;
        this.DModel_Name =DModel_Name;
    }
}
