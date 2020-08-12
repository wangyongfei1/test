package com.wisdomconstruction.wisdomConstruction.dao.base.enums.highmodulusBasePoint;

/**
 * 高支模设备基准表属性列名枚举
 */
public enum HighModulusBasePointDOPropertiesEnum {
    /**
     * 项目编号
     */
    PROJECT_NO("projectNo", "project_no"),
    /**
     * 设备编号
     */
    SERIAL_NUMBER("serialNumber", "serial_number"),
    /**
     * 水平位移基准
     */
    HORIZONTAL_DISPLACEMENT("horizontalDisplacement", "horizontal_displacement"),
    /**
     * 模板沉降基准
     */
    SETTLEMENT_TEMPLATE("settlementTemplate", "settlement_template"),
    /**
     * 立杆倾角基准
     */
    POLING_ANGLE("polingAngle", "poling_angle"),
    /**
     * 水平杆倾角基准
     */
    HORIZONTAL_ANGLE("horizontalAngle", "horizontal_angle"),
    /**
     * 承重基准
     */
    BEARING("bearing", "bearing"),
    /**
     * 水平位移偏移阈值
     */
    HORIZONTAL_DISPLACEMENT_OFFSET("horizontalDisplacementOffset", "horizontal_displacement"),
    /**
     * 模板沉降偏移阈值
     */
    SETTLEMENT_TEMPLATE_OFFSET("settlementTemplateOffset", "settlement_template_offset"),
    /**
     * 立杆倾角偏移阈值
     */
    POLING_ANGLE_OFFSET("polingAngleOffset", "poling_angle_offset"),
    /**
     * 水平杆倾角偏移阈值
     */
    HORIZONTAL_ANGLE_OFFSET("horizontalAngleOffset", "horizontal_angle_offset"),
    /**
     * 承重偏移阈值
     */
    BEARING_OFFSET("bearingOffset", "bearing_offset"),
    /**
     * 安装高度
     */
    HEIGHT("height", "height"),

    /**
     * 创建时间
     */
    GMT_CREATE("gmtCreate", "gmt_create");

    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    HighModulusBasePointDOPropertiesEnum(String property, String column) {
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
