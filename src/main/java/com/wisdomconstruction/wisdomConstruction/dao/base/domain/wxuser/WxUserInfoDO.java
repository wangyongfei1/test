package com.wisdomconstruction.wisdomConstruction.dao.base.domain.wxuser;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wangyongfei
 * @date 2020/7/9 18:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("wx_public_number_user_info")
public class WxUserInfoDO extends BaseBizModel {

    @TableField("open_id")
    private String openId;

    @TableField("union_id")
    private String unionId;
}