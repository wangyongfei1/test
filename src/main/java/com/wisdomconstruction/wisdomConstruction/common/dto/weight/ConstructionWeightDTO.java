package com.wisdomconstruction.wisdomConstruction.common.dto.weight;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ConstructionWeightDTO {

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 绑定的设备
     */
    private String deviceNo;

    /**
     * 货物净重
     */
    private Float cargoNetWeight;

    /**
     * 货物毛重
     */
    private Float cargoGrossWeight;

    /**
     * 货物皮重
     */
    private Float cargoSkinWeight;

    /**
     * 进场时间
     */
    private Date inTime;

    /**
     * 车牌号码
     */
    private String licensePlateNo;

    /**
     * 货物名称
     */
    private String cargoName;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 货物图片
     */
    private String cargoPhoto;
    /**
     * 流水号
     */
    private String serialNumber;
    /**
     * 操作员
     */
    private String operator;
    /**
     * 时间
     */
    private Date gmtCreate;

}