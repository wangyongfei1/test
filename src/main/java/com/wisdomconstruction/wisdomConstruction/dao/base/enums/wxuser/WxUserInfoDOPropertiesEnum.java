package com.wisdomconstruction.wisdomConstruction.dao.base.enums.wxuser;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangyongfei
 * @date 2020/7/9 18:44
 */
@Getter
@AllArgsConstructor
public enum  WxUserInfoDOPropertiesEnum {

    OPEN_ID("openId","open_id"),

    UNION_ID("unionId","union_id");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;
}