package com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 高支模设备表
 * </p>
 *
 * @author huangc
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_high_modulus")
public class ConstructionHighModulusDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;


    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 设备编号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 水平位移
     */
    @TableField("horizontal_displacement")
    private String horizontalDisplacement;

    /**
     * 模板沉降
     */
    @TableField("settlement_template")
    private String settlementTemplate;

    /**
     * 立杆倾角
     */
    @TableField("poling_angle")
    private String polingAngle;

    /**
     * 水平杆倾角
     */
    @TableField("horizontal_angle")
    private String horizontalAngle;

    /**
     * 承重
     */
    @TableField("bearing")
    private String bearing;

    /**
     * 当前监测点状态 0：正常；1：水平位移预警；2：水平位移报警；3：沉降预警；4：沉降报警；5：立杆倾角预警；6：立杆倾角报警；7：水平倾角预警；8：水平倾角报警；9：承重预警；A：承重报警；
     */
    @TableField("site_status")
    private String siteStatus;

    /**
     * 时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;


}
