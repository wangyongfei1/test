package com.wisdomconstruction.wisdomConstruction.dao.base.domain.equipment;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_equipment")
public class ConstructionEquipmentDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;


    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 设备类型 0：扬尘 1：塔机 2：施工升降机 3：深基坑 4：卸料台 5：高支模 6：智能电表 7：智能水表
     */
    @TableField("device_type")
    private Integer deviceType;

    /**
     * 获取数据方式 1-主动获取 2-自动接收
     */
    @TableField("get_data_method")
    private Integer getDataMethod;

    /**
     * 是否连接DTU 0：有 1：否
     */
    @TableField("has_dtu")
    private Integer hasDtu;

    /**
     * dtu唯一标识码imei
     */
    @TableField("dtu_imei")
    private String dtuImei;

    /**
     * 设备状态 0-在线（正常） 1-离线（异常）
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
