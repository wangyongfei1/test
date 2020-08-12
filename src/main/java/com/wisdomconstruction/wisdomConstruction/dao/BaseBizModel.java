package com.wisdomconstruction.wisdomConstruction.dao;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @功能:实体基础类
 * @项目名:creditpay-common-pojo-jar
 * @作者:20314@etransfar.com(zhangbin)
 * @日期:2018年8月14日下午8:04:09
 */
@Setter
@Getter
@Accessors(chain = true)
public class BaseBizModel extends Model<BaseBizModel> implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4641073174579368727L;
    /**
     * 主键ID
     */
    @JsonIgnore
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonIgnore
    @TableField("is_delete")
    private Integer isDelete;
    /**
     * 创建日期
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 更新日期
     */
    @TableField("gmt_modified")
    private Date gmtModified;
    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;
    /**
     * 更新人
     */
    @JsonIgnore
    @TableField("modifier")
    private String modifier;

    /**
     * (non-Javadoc)
     *
     * @see com.baomidou.mybatisplus.activerecord.Model#pkVal()
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }

    /**
     * 设置创建人创建时间
     */
    public BaseBizModel setCreatorInfo() {
        setCreator("System");
        setGmtCreate(new Date());
        return this;
    }

    /**
     * 设置修改人修改时间
     */
    public BaseBizModel setModifierInfo() {
        setModifier("System");
        setGmtModified(new Date());
        return this;
    }

}
