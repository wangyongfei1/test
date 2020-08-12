package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx;

import lombok.Data;

/**
 * @Author: liujl
 * @DateTime: 2020-06-30 17:37
 * @Description: TODO
 */
@Data
public class WxAppUserInfoVo {

    private String openId;

    private String nickName;

    private String gender;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

    private String unionId;

    private String watermark;
}
