package com.wisdomconstruction.wisdomConstruction.dao.base.domain.company;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wangyongfei
 * @date 2020/7/9 16:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("company_project_info")
public class CompanyProjectInfoDO extends BaseBizModel {

    /**
     * 企业编号
     */
    @TableField("company_no")
    private String companyNo;

    /**
     * 项目地址
     */
    @TableField("project_address")
    private String projectAddress;

    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

}