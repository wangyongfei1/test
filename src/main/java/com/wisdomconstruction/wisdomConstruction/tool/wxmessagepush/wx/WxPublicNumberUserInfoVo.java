package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx;

import lombok.Data;

/**
 * @Author: liujl
 * @DateTime: 2020-06-29 10:40
 * @Description: 微信公众号用户信息
 */
@Data
public class WxPublicNumberUserInfoVo {

    //用户的唯一标识
    private String openId;

    //只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
    private String unionId;

    //用户昵称
    private String nickname;

    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String sex;

    //用户个人资料填写的省份
    private String province;

    //普通用户个人资料填写的城市
    private String city;

    //国家，如中国为CN
    private String country;

}
