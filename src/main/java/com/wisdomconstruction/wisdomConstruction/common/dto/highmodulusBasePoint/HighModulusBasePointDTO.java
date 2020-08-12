package com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
public class HighModulusBasePointDTO {
    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 设备编号
     */
    private String serialNumber;

    /**
     * 水平位移基准
     */
    private String horizontalDisplacement;

    /**
     * 模板沉降基准
     */
    private String settlementTemplate;

    /**
     * 立杆倾角基准
     */
    private String polingAngle;

    /**
     * 水平杆倾角基准
     */
    private String horizontalAngle;

    /**
     * 承重基准
     */
    private String bearing;
    /**
     * 水平位移偏移阈值
     */
    private Float horizontalDisplacementOffset;

    /**
     * 模板沉降偏移阈值
     */
    private Float settlementTemplateOffset;

    /**
     * 立杆倾角偏移阈值
     */
    private Float polingAngleOffset;

    /**
     * 水平杆倾角偏移阈值
     */
    private Float horizontalAngleOffset;

    /**
     * 承重偏移阈值
     */
    private Float bearingOffset;


    /**
     * 安装高度
     */
    private Integer height;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}