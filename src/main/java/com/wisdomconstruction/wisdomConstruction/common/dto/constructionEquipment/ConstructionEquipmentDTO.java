package com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConstructionEquipmentDTO {

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型 0：扬尘 1：塔机 2：施工升降机 3：深基坑 4：卸料台 5：高支模 6：智能电表 7：智能水表
     */
    private Integer deviceType;

    /**
     * 是否连接DTU 0：有 1：否
     */
    private Integer hasDtu;

    /**
     * 获取数据方式 1-主动获取 2-自动接收
     */
    private Integer getDataMethod;

    /**
     * dtu唯一标识码imei
     */
    private String dtuImei;

    /**
     * 设备状态 0-在线（正常） 1-离线（异常）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}