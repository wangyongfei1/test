package com.wisdomconstruction.wisdomConstruction.common.dto.goods;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConstructionGoodsDTO {

    /**
     * 货物或者供货商名称
     */
    private String name;
    /**
     * 类型  0：货名  1：供货商
     */
    private Integer goodsType;

}