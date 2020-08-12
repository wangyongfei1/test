package com.wisdomconstruction.wisdomConstruction.dao.base.enums.weight;

public enum ConstructionWeightDOPropertiesEnum {
    /**
     * 项目编号
     */
    PROJECT_NO("projectNo", "project_no"),
    /**
     * 绑定的设备
     */
    DEVICE_NO("deviceNo", "device_no"),
    /**
     * 货物净重
     */
    CARGO_NET_WEIGHT("cargoNetWeight", "cargo_net_weight"),
    /**
     * 货物毛重
     */
    CARGO_GROSS_WEIGHT("cargoGrossWeight", "cargo_gross_weight"),
    /**
     * 货物皮重
     */
    CARGO_SKIN_WEIGHT("cargoSkinWeight", "cargo_skin_weight"),
    /**
     * 进场时间
     */
    IN_TIME("inTime", "in_time"),
    /**
     * 车牌号码
     */
    LICENSE_PLATE_NO("licensePlateNo", "license_plate_no"),
    /**
     * 货物名称
     */
    CARGO_NAME("cargoName", "cargo_name"),
    /**
     * 供应商
     */
    SUPPLIER("supplier", "supplier"),
    /**
     * 备注
     */
    REMARKS("remarks", "remarks"),
    /**
     * 流水号
     */
    SERIAL_NUMBER("serialNumber", "serial_number"),
    /**
     * 操作员
     */
    OPERATOR("operator", "operator"),
    /**
     * 时间
     */
    GMT_CREATE("gmtCreate", "gmt_create"),
    /**
     * 货物图片
     */
    CARGO_PHOTO("cargoPhoto", "cargo_photo");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    ConstructionWeightDOPropertiesEnum(String property, String column) {
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
