package com.wisdomconstruction.wisdomConstruction.dao.base.enums.companyproject;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wangyongfei
 * @date 2020/7/9 16:56
 */
@Getter
@AllArgsConstructor
public enum CompanyProjectInfoDOPropertiesEnum {

    /**
     * 企业编号
     */
    COMPANY_NO("company_no","companyNo"),

    /**
     * 项目地址
     */
    PROJECT_ADDRESS("project_address","projectAddress"),
    /**
     * 项目编号
     */
    PROJECT_NO("project_no","projectNo");

    /**
     * 表字段名
     */
    private String column;

    /**
     * 属性名
     */
    private String property;
}
