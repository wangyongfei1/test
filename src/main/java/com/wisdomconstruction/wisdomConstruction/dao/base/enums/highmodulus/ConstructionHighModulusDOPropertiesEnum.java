package com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulus;

/**
 * <p>
 * 高支模设备表属性列名枚举
 * </p>
 *
 * @author huangc
 * @since 2020-05-07
 */
public enum ConstructionHighModulusDOPropertiesEnum {

    /**
     * 项目编号
     */
    PROJECT_NO("projectNo", "project_no"),
    /**
     * 设备编号
     */
    SERIAL_NUMBER("serialNumber", "serial_number"),
    /**
     * 水平位移
     */
    HORIZONTAL_DISPLACEMENT("horizontalDisplacement", "horizontal_displacement"),
    /**
     * 模板沉降
     */
    SETTLEMENT_TEMPLATE("settlementTemplate", "settlement_template"),
    /**
     * 立杆倾角
     */
    POLING_ANGLE("polingAngle", "poling_angle"),
    /**
     * 水平杆倾角
     */
    HORIZONTAL_ANGLE("horizontalAngle", "horizontal_angle"),
    /**
     * 承重
     */
    BEARING("bearing", "bearing"),
    /**
     * 当前监测点状态 0：正常；1：水平位移预警；2：水平位移报警；3：沉降预警；4：沉降报警；5：立杆倾角预警；6：立杆倾角报警；7：水平倾角预警；8：水平倾角报警；9：承重预警；A：承重报警；
     */
    SITE_STATUS("siteStatus", "site_status"),
    /**
     * 时间
     */
    GMT_CREATE("gmtCreate","gmt_create");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    ConstructionHighModulusDOPropertiesEnum(String property, String column) {
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
