package com.wisdomconstruction.wisdomConstruction.dao.base.domain.siteDust;

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
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * <p>
 * 扬尘设备表
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_site_dust")
public class ConstructionSiteDustDO extends BaseBizModel {

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
     * 温度
     */
    @NotBlank(message = "温度不能为空")
    @Pattern(regexp = "^[-+]?[0-9]+(\\.[0-9]+)?$",message = "温度数据错误")
    @TableField("temperature")
    private String temperature;

    /**
     * 湿度
     */
    @NotBlank(message = "湿度数据不能为空")
    @Min(value = 0,message = "湿度数据错误")
    @TableField("humidity")
    private String humidity;

    /**
     * 气压
     */
    @NotBlank(message = "气压数据不能为空")
    @Min(value = 0,message = "气压数据错误")
    @TableField("air_pressure")
    private String airPressure;

    /**
     * 风速
     */
    @NotBlank(message = "风速数据不能为空")
    @Min(value = 0,message = "风速数据错误")
    @TableField("wind_speed")
    private String windSpeed;

    /**
     * 风向
     */
    @NotBlank(message = "风向数据不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{0,}$",message = "风向数据错误")
    @TableField("direction_wind")
    private String directionWind;

    /**
     * PM2.5
     */
    @NotBlank(message = "PM2.5数据不能为空")
    @Min(value = 0,message = "PM2.5数据错误")
    @TableField("particulate_25")
    private String particulate25;

    /**
     * PM10
     */
    @NotBlank(message = "PM10数据不能为空")
    @Min(value = 0,message = "PM10数据错误")
    @TableField("particulate_10")
    private String particulate10;

    /**
     * 悬浮颗粒(TSP)
     */
    @NotBlank(message = "悬浮颗粒数据不能为空")
    @Min(value = 0,message = "悬浮颗粒数据错误")
    @TableField("suspended_particles")
    private String suspendedParticles;

    /**
     * 噪音
     */
    @NotBlank(message = "噪音数据不能为空")
    @Min(value = 0,message = "噪音数据错误")
    @TableField("noise")
    private String noise;

    /**
     * 数据采集时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "数据采集时间错误")
    @TableField("transmission_time")
    private Date transmissionTime;


}
