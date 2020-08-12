package com.wisdomconstruction.wisdomConstruction.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础字段属性枚举
 *
 * @author 20314@etransfar.com(zhangbin)
 * @CreateDate 2019/7/31 10:56
 */
@Getter
@AllArgsConstructor
public enum BasePropertiesEnum {

    ID("id", "id"),

    IS_DELETE("isDelete", "is_delete"),

    GMT_CREATE("gmtCreate", "gmt_create"),

    GMT_MODIFIED("gmtModified", "gmt_modified"),

    CREATOR("creator", "creator"),

    MODIFIER("modifier", "modifier");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;
}