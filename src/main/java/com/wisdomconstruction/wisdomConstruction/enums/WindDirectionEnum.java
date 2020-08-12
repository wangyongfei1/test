package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.Getter;

/**
 * 风向值
 */
@Getter
public enum WindDirectionEnum {


    NORTH_WIND(0, "北风"),
    NORTH_EAST_WIND(1, "东北风"),
    EAST_WIND(2, "东风"),
    SOUTH_EAST_WIND(3, "东南风"),
    SOUTH_WIND(4, "南风"),
    SOUTH_WEST_WIND(5, "西南风"),
    WEST_WIND(6, "西风"),
    NORTH_WEST_WIND(7, "西北风"),

    ;

    private Integer code;
    private String remark;

    WindDirectionEnum(Integer code, String remark) {
        this.code =code;
        this.remark =remark;
    }
}
