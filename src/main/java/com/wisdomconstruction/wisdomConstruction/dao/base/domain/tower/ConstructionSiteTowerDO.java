package com.wisdomconstruction.wisdomConstruction.dao.base.domain.tower;

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
 * 塔机设备表
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_site_tower")
public class ConstructionSiteTowerDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;


    /**
     * 项目编号
     */
    @TableField("project_no")
    private String projectNo;

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空")
    @TableField("device_no")
    private String deviceNo;

    /**
     * 制动状态
     */
    @NotBlank(message = "制动状态不能为空")
    @TableField("braking_state")
    private String brakingState;

    /**
     * 起重量
     */
    @NotNull(message = "起重量数据不能为空")
    @Min(value = 0,message = "起重量数据错误")
    @TableField("elevating_capacity")
    private Double elevatingCapacity;

    /**
     * 高度
     */
    @NotNull(message = "高度数据不能为空")
    @TableField("height")
    private Double height;

    /**
     * 幅度
     */
    @NotNull(message = "幅度数据不能为空")
    @TableField("range")
    private Double range;

    /**
     * 回转
     */
    @NotNull(message = "回转数据不能为空")
    @TableField("rotation")
    private Double rotation;

    /**
     * 倾角
     */
    @NotNull(message = "倾角数据不能为空")
    @TableField("tilt_angle")
    private Double tiltAngle;

    /**
     * 倾斜百分比
     */
    @NotNull(message = "倾斜百分比不能为空")
    @Min(value = 0,message = "倾斜百分比错误")
    @TableField("tilt_percentage")
    private Double tiltPercentage;

    /**
     * 力距百分比
     */
    @NotNull(message = "力距百分比不能为空")
    @Min(value = 0,message = "力距百分比错误")
    @TableField("torque_percentage")
    private Double torquePercentage;

    /**
     * 重量百分比
     */
    @NotNull(message = "重量百分比不能为空")
    @Min(value = 0,message = "重量百分比错误")
    @TableField("weight_percentage")
    private Double weightPercentage;

    /**
     * 风速
     */
    @NotNull(message = "风速不能为空")
    @Min(value = 0,message = "风速数据错误")
    @TableField("wind_speed")
    private Double windSpeed;

    /**
     * 风级
     */
    @NotNull(message = "风级不能为空")
    @Min(value = 0,message = "风级数据错误")
    @TableField("beaufort_scale")
    private Integer beaufortScale;

    /**
     * 风速百分比
     */
    @NotNull(message = "风速百分比数据不能为空")
    @Min(value = 0,message = "风速百分比数据错误")
    @TableField("wind_speed_percentage")
    private Double windSpeedPercentage;

    /**
     * 报警原因
     */
    @NotNull(message = "高度数据不能为空")
    @TableField("alarm_reason")
    private Integer alarmReason;

    /**
     * 数据采集时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("transmission_time")
    @NotNull(message = "数据采集时间错误")
    private Date transmissionTime;

}
