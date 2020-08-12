package com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulusBasePoint;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_high_modulus_base_point")
public class HighModulusBasePointDO extends BaseBizModel {

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
     * 水平位移基准
     */
    @TableField("horizontal_displacement")
    private String horizontalDisplacement;

    /**
     * 模板沉降基准
     */
    @TableField("settlement_template")
    private String settlementTemplate;

    /**
     * 立杆倾角基准
     */
    @TableField("poling_angle")
    private String polingAngle;

    /**
     * 水平杆倾角基准
     */
    @TableField("horizontal_angle")
    private String horizontalAngle;

    /**
     * 承重基准
     */
    @TableField("bearing")
    private String bearing;

    /**
     * 水平位移偏移阈值
     */
    @TableField("horizontal_displacement_offset")
    private String horizontalDisplacementOffset;

    /**
     * 模板沉降偏移阈值
     */
    @TableField("settlement_template_offset")
    private String settlementTemplateOffset;

    /**
     * 立杆倾角偏移阈值
     */
    @TableField("poling_angle_offset")
    private String polingAngleOffset;

    /**
     * 水平杆倾角偏移阈值
     */
    @TableField("horizontal_angle_offset")
    private String horizontalAngleOffset;

    /**
     * 承重偏移阈值
     */
    @TableField("bearing_offset")
    private String bearingOffset;

    /**
     * 安装高度
     */
    @TableField("height")
    private Integer height;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
}