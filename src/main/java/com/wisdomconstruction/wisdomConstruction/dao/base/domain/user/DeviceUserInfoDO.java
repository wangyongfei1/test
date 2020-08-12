package com.wisdomconstruction.wisdomConstruction.dao.base.domain.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备数据推送用户
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("device_user_info")
public class DeviceUserInfoDO extends BaseBizModel {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 名称
     */
    @TableField("really_name")
    private String reallyName;

    /**
     * 联系电话
     */
    @TableField("user_mobile")
    private String userMobile;

    /**
     * 设备编号集合
     */
    @TableField("device_no_list")
    private String deviceNoList;



}
