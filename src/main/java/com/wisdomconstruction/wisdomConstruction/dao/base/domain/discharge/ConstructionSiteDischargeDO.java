package com.wisdomconstruction.wisdomConstruction.dao.base.domain.discharge;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 卸料平台设备表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_site_discharge")
public class ConstructionSiteDischargeDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空")
    @TableField("device_no")
    private String deviceNo;

    /**
     * 电量百分比
     */
    @NotNull(message = "电量百分比不能为空")
    @Min(value = 0,message = "电量百分比数据错误")
    @TableField("electric_quantity")
    private Float electricQuantity;

    /**
     * 重量
     */
    @NotNull(message = "重量不能为空")
    @Min(value = 0,message = "重量数据错误")
    @TableField("weight")
    private Float weight;

    /**
     * 偏置值
     */
    @NotNull(message = "偏置值不能为空")
    @Min(value = 0,message = "偏置值数据错误")
    @TableField("bias")
    private Float bias;

    /**
     * 数据状态 0正常 1预警 2报警
     */
    @NotNull(message = "数据状态不能为空")
    @Min(value = 0,message = "数据状态数据错误")
    @TableField("upstate")
    private Integer upstate;

    /**
     * 预警重量
     */
    @NotNull(message = "预警重量不能为空")
    @Min(value = 0,message = "预警重量数据错误")
    @TableField("early_warning_weight")
    private Float earlyWarningWeight;

    /**
     * 报警重量
     */
    @NotNull(message = "报警重量不能为空")
    @Min(value = 0,message = "报警重量数据错误")
    @TableField("alarm_weight")
    private Float alarmWeight;

    /**
     * 数据采集时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("transmission_time")
    @NotNull(message = "数据采集时间错误")
    private Date transmissionTime;

}
