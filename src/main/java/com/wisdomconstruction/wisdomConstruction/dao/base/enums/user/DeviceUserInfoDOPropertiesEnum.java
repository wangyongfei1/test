package com.wisdomconstruction.wisdomConstruction.dao.base.enums.user;

/**
 * <p>
 * 设备数据推送用户属性列名枚举
 * </p>
 *
 * @author huangc
 * @since 2020-05-01
 */
public enum DeviceUserInfoDOPropertiesEnum {

    /**
     * 账号
     */
    USER_NAME("userName", "user_name"),
    /**
     * 密码
     */
    PASSWORD("password", "password"),
    /**
     * 名字
     */
    REALLY_NAME("reallyName", "really_name"),
    /**
     * 联系方式
     */
    USER_MOBILE("userMobile", "user_mobile"),
    /**
     * 设备集合
     */
    DEVICE_NO_LIST("deviceNoList", "device_no_list")
    ;

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    DeviceUserInfoDOPropertiesEnum(String property, String column) {
        this.property = property;
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }
}
