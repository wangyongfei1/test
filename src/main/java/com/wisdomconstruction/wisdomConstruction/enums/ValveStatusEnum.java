package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.Getter;

/**
 * 喷淋阀门开关状态
 */
@Getter
public enum ValveStatusEnum {

    CLOSE(0, "关阀"),
    OPEN(1, "开阀"),
    ;

    private Integer code;
    private String status;

    ValveStatusEnum(Integer code, String status) {
        this.code =code;
        this.status =status;
    }
}
