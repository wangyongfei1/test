package com.wisdomconstruction.wisdomConstruction.common.vo.weight;

import lombok.Data;

import java.util.Date;
@Data
public class ConstructionWeightVO {

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 绑定的设备
     */
    private String deviceNo;
    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 车牌号码
     */
    private String licensePlateNo;
    /**
     * 时间
     */
    private Date gmtCreate;
}