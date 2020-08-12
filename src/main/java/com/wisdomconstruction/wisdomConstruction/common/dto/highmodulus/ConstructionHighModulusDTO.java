package com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ConstructionHighModulusDTO {

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 设备编号
     */
    private String serialNumber;

    /**
     * 水平位移
     */
    private String horizontalDisplacement;

    /**
     * 模板沉降
     */
    private String settlementTemplate;

    /**
     * 立杆倾角
     */
    private String polingAngle;

    /**
     * 水平杆倾角
     */
    private String horizontalAngle;

    /**
     * 承重
     */
    private String bearing;

    /**
     * 当前监测点状态 0：正常；1：水平位移预警；2：水平位移报警；3：沉降预警；4：沉降报警；5：立杆倾角预警；6：立杆倾角报警；7：水平倾角预警；8：水平倾角报警；9：承重预警；A：承重报警；
     */
    private String siteStatus;

    /**
     * 时间
     */
    private Date gmtCreate;


}