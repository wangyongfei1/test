package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx;

import lombok.Data;

/**
 * @Author: liujl
 * @DateTime: 2020-06-23 10:43
 * @Description: 微信授权后获取到的数据
 */
@Data
public class WxResultInfo {

    private String openId;

    private String sessionKey;

    private String accessToken;

    private String expiresIn;

    //微信公众号-用户授权的作用域，使用逗号（,）分隔
    private String scope;
    //微信公众号-用户刷新access_token
    private String refreshToken;

    private String unionId;
}
