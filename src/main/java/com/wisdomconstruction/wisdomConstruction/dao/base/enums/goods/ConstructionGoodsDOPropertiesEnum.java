package com.wisdomconstruction.wisdomConstruction.dao.base.enums.goods;

public enum ConstructionGoodsDOPropertiesEnum {
    /**
     * 货物或者供货商名称
     */
    NAME("name", "name"),
    /**
     * 类型  0：货名  1：供货商
     */
    GOODS_TYPE("goodsType", "goods_type");
    /**
     * 属性名
     */
    private String property;

    /**
     * 表字段名
     */
    private String column;

    ConstructionGoodsDOPropertiesEnum(String property, String column) {
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
