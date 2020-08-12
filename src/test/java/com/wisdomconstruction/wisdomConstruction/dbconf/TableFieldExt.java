package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import org.springframework.beans.BeanUtils;

/**
 * 表字段信息 扩展
 *
 * @Author chenhongding
 * @since 2018-08-29.
 */
public class TableFieldExt extends TableField {
    private String upperColumnName;


    public TableFieldExt(StrategyConfig strategyConfig, TableField tableField) {
        BeanUtils.copyProperties(tableField, this);
        this.setPropertyName(strategyConfig, tableField.getPropertyName());
        this.setUpperColumnName(tableField.getName().toUpperCase());
    }

    public String getUpperColumnName() {
        return upperColumnName;
    }

    public void setUpperColumnName(String upperPropertyName) {
        this.upperColumnName = upperPropertyName;
    }

}
