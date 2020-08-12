package com.wisdomconstruction.wisdomConstruction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扬尘数据获取方式枚举
 * @author wangyongfei
 * @date 2020/6/29 20:47
 */
@Getter
@AllArgsConstructor
public enum DustGetDataMethodEnum {

    ACTIVE_ACCESS(1,"主动获取扬尘数据"),
    AUTOMATIC_RECEPTION(2,"自动接收扬尘数据");

    /**
     * 获取方式
     */
    private Integer methodNo;
    /**
     * 描述信息
     */
    private String description;


}