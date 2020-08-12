package com.wisdomconstruction.wisdomConstruction.dao.base.domain.weight;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 地磅设备表
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_site_cargo_info")
public class ConstructionWeightDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;

    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 绑定的设备
     */
    @TableField("device_no")
    private String deviceNo;

    /**
     * 货物净重
     */
    @TableField("cargo_net_weight")
    private Float cargoNetWeight;

    /**
     * 货物毛重
     */
    @TableField("cargo_gross_weight")
    private Float cargoGrossWeight;

    /**
     * 货物皮重
     */
    @TableField("cargo_skin_weight")
    private Float cargoSkinWeight;

    /**
     * 进场时间
     */
    @TableField("in_time")
    private Date inTime;

    /**
     * 车牌号码
     */
    @TableField("license_plate_no")
    private String licensePlateNo;

    /**
     * 货物名称
     */
    @TableField("cargo_name")
    private String cargoName;

    /**
     * 供应商
     */
    @TableField("supplier")
    private String supplier;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 货物图片
     */
    @TableField("cargo_photo")
    private String cargoPhoto;

    /**
     * 流水号
     */
    @TableField("serial_number")
    private String serialNumber;

    /**
     * 操作员
     */
    @TableField("operator")
    private String operator;
    /**
     * 时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

}