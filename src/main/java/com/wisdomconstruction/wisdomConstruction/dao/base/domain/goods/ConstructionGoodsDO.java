package com.wisdomconstruction.wisdomConstruction.dao.base.domain.goods;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("construction_goods_info")
public class ConstructionGoodsDO extends BaseBizModel {
    private static final long serialVersionUID = 1L;

    /**
     * 货物或者供货商名称
     */
    @TableField("name")
    private String name;
    /**
     * 类型  0：货名  1：供货商
     */
    @TableField("goods_type")
    private Integer goodsType;

}