package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.AesUtils;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx.WxAppUserInfoVo;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx.WxPublicNumberUserInfoVo;
import com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx.WxResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: liujl
 * @DateTime: 2020-06-22 17:18
 * @Description: 微信工具类
 */
@Slf4j
public class WxUtils {

    /**
     * 微信公众号
     */
    //微信公众号TOKEN
    private static final String WX_PUBLIC_NUMBER_TOKEN = "123456";

    //微信公众号APPID
    private static final String WX_PUBLIC_NUMBER_APPID = "wxdf675e57fb94248d";

    //微信公众号APPSECRET
    private static final String WX_PUBLIC_NUMBER_APPSECRET = "ec44e2b0b8577ade495f93e0d13f0a0d";

    //微信公众号授权认证地址
    private static final String WX_PUBLIC_NUMBER_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    //微信公众号授权后重定向的回调链接地址
    private static final String WX_PUBLIC_NUMBER_CALLBACK_URL = "https://arearae.cn/labor/wxApi/wxCallBack";

    //微信公众号获取open_id和access_token地址
    private static final String WX_PUBLIC_NUMBER_OPENID_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    //微信公众号获取access_token地址
    private static final String WX_PUBLIC_NUMBER_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //微信公众号获取网页授权微信用户信息地址
    private final static String WX_PUBLIC_NUMBER_SNS_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESSTOKEN&openid=OPENID&lang=zh_CN";

    //微信公众号消息推送URL
    private final static String WX_PUBLIC_NUMBER_MESSAGE_PUSH_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 微信小程序
     */
    //微信小程序APPID
    private static final String APPID = "wx66ed72be3c35e6c8";

    //微信小程序与APPSECRET
    private static final String APPSECRET = "69da0edb6301a8f5de4d0975889a103a";

    //微信小程序获取openId URL
    private static final String WX_APP_OPENID_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    //获取accessToken url
    private static final String WX_APP_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 微信公众号接口配置认证
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     * @throws IOException
     */
    public static Boolean wxVerify(String signature, String timestamp, String nonce){
        //通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (checkSignature(signature, timestamp, nonce)){
            return true;
        }
        return false;
    }

    /**
     * 微信公众号授权
     * @return
     */
    public static String wxAuthorize(){
        //拼装授权接口
        String authorize_url = "";
        try {
            //拼装回调地址
            String callBackUrl = URLEncoder.encode(WX_PUBLIC_NUMBER_CALLBACK_URL,"UTF-8");
            authorize_url = WX_PUBLIC_NUMBER_AUTH_URL.replace("APPID",WX_PUBLIC_NUMBER_APPID).replace("REDIRECT_URI",callBackUrl);
            log.info("======>>>>>>authorize_url:{}",authorize_url);
        } catch (UnsupportedEncodingException e) {
            log.error("======>>>>>>urlEncode处理异常:{}",e);
        }
        return authorize_url;
    }

    /**
     * 获取微信公众号AccessToken
     *
     * 注意：此AccessToken为基础支持的token 用来进行消息推送
     *
     * @return
     */
    public static WxResultInfo getWxPublicNumberAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        Map<String,String> params = new HashMap<>();
        params.put("appid",WX_PUBLIC_NUMBER_APPID);
        params.put("secret",WX_PUBLIC_NUMBER_APPSECRET);
        params.put("garnt_type","client_credential");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(WX_PUBLIC_NUMBER_ACCESSTOKEN_URL.replace("APPID", WX_PUBLIC_NUMBER_APPID).replace("APPSECRET", WX_PUBLIC_NUMBER_APPSECRET), String.class, params);
        log.info("======>>>>>>微信公众号AccessToken:{}",responseEntity.getBody());

        return buildWxResultInfoData(responseEntity.getBody());
    }

    /**
     * 微信公众号根据授权码获取当前授权人员的openid
     *
     * 注意：这里获取到的AccessToken和getWxPublicNumberAccessToken()方法获取到的token是不一样的，此AccessToken用来获取用户信息
     *
     * @param code
     * @return
     */
    public static WxResultInfo getWxPublicNumberOpenId(String code){
        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        Map<String,String> params = new HashMap<>();
        params.put("appid",WX_PUBLIC_NUMBER_APPID);
        params.put("secret",WX_PUBLIC_NUMBER_APPSECRET);
        params.put("code",code);
        params.put("grant_type","authorization_code");

        //拼装url
        String url = WX_PUBLIC_NUMBER_OPENID_ACCESSTOKEN_URL.replace("APPID", WX_PUBLIC_NUMBER_APPID).replace("SECRET", WX_PUBLIC_NUMBER_APPSECRET).replace("CODE", code);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, params);
        log.info("======>>>>>>微信公众号OpenId:{}",responseEntity.getBody());

        return buildWxResultInfoData(responseEntity.getBody());
    }

    /**
     * 微信公众号 根据openid&accessToken获取当前授权用户的个人信息
     * @param openId
     * @param accessToken
     */
    public static WxPublicNumberUserInfoVo getWxPublicNumberUserInfo(String openId, String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        Map<String,String> params = new HashMap<>();
        params.put("openid",openId);
        params.put("access_token", accessToken);
        params.put("lang","zh_CN");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(WX_PUBLIC_NUMBER_SNS_USER_INFO_URL.replace("ACCESSTOKEN", accessToken).replace("OPENID", openId), String.class, params);
        log.info("======>>>>>>微信公众号userInfo:{}",responseEntity.getBody());

        return buildUserInfoData(responseEntity.getBody());
    }

    /**
     * 微信公众号 根据授权码获取当前用户个人信息
     * @param code
     */
    public static WxPublicNumberUserInfoVo getWxPublicNumberUserInfo(String code){
        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        WxResultInfo wxResultInfo = getWxPublicNumberOpenId(code);

        Map<String,String> params = new HashMap<>();
        params.put("openid",wxResultInfo.getOpenId());
        params.put("access_token", wxResultInfo.getAccessToken());
        params.put("lang","zh_CN");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(WX_PUBLIC_NUMBER_SNS_USER_INFO_URL.replace("ACCESSTOKEN", wxResultInfo.getAccessToken()).replace("OPENID", wxResultInfo.getOpenId()), String.class, params);
        log.info("======>>>>>>微信公众号userInfo:{}",responseEntity.getBody());

        return buildUserInfoData(responseEntity.getBody());
    }

    /***
     * 微信公众号消息推送
     * @param openid
     * @param templateId
     * @param accessToken
     * @param dataMap 推送消息内容的参数
     * @param miniprogramMap 跳转小程序页面需要的参数
     * @return
     */
    public static Boolean wxPublicNumberMessgesPush(String openid,String templateId,String accessToken,Map<String,Object> dataMap, Map<String,Object> miniprogramMap){

        RestTemplate restTemplate = new RestTemplate();

        //拼接参数Map
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("touser",openid);
        paramMap.put("template_id",templateId);
        paramMap.put("appid",WX_PUBLIC_NUMBER_APPID);

        //miniprogramMap 跳转小程序页面需要的参数
        if (Objects.nonNull(miniprogramMap)){
            paramMap.put("miniprogram",miniprogramMap);
        }

        paramMap.put("data",dataMap);

        log.info("======>>>>>>paramMap:{}",paramMap);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(WX_PUBLIC_NUMBER_MESSAGE_PUSH_URL.replace("ACCESS_TOKEN", accessToken), paramMap, String.class);

        log.info("======>>>>>>消息推送:{}",responseEntity.getBody());
        //解析返回的Body
        JSONObject jsonObject = JSON.parseObject(responseEntity.getBody());
        if (StringUtils.isBlank(jsonObject.getString("msgid"))){
            log.info("======>>>>>>消息推送失败,errcode:{},errmsg:{}",jsonObject.getString("errcode"),jsonObject.getString("errmsg"));
            return false;
        }
        log.info("======>>>>>>消息推送成功,msgid:{}",jsonObject.getString("msgid"));
        return true;
    }

    /**
     * 微信小程序根据登录凭证获取OpenId
     * @param code 登录凭证
     * @return
     */
    public static WxResultInfo getWxAppOpenId(String code){

        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        Map<String,String> params = new HashMap<>();
        params.put("appid",APPID);
        params.put("secret",APPSECRET);
        params.put("js_code",code);
        params.put("grant_type","authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(WX_APP_OPENID_URL.replace("APPID",APPID).replace("SECRET",APPSECRET).replace("JSCODE",code), String.class, params);

        return buildWxResultInfoData(responseEntity.getBody());
    }

    /**
     * 解密微信小程序用户数据
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    public static WxAppUserInfoVo getWxAppUserInfo(String encryptedData, String sessionKey, String iv){
        String wxUserInfo = AesUtils.wxDecrypt(encryptedData, sessionKey, iv);
        log.info("======>>>>>>解密后的用户数据:{}",wxUserInfo);
        return buildWxAppUserInfoData(wxUserInfo);
    }

    /**
     * 微信小程序获取AccessToken
     * @return
     */
    public static WxResultInfo getWxAppAccessToken(){

        RestTemplate restTemplate = new RestTemplate();
        setRestTemplateCharset(restTemplate);

        Map<String, String> params = new HashMap<>();
        params.put("appid", APPID);
        params.put("secret", APPSECRET);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(WX_APP_ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET), String.class, params);

        return buildWxResultInfoData(responseEntity.getBody());
    }

    /**
     * 组装获取到的用户数据
     * @param body
     * @return
     */
    private static WxPublicNumberUserInfoVo buildUserInfoData(String body){

        JSONObject jsonObject = JSON.parseObject(body);

        WxPublicNumberUserInfoVo wxPublicNumberUserInfoVo = new WxPublicNumberUserInfoVo();
        wxPublicNumberUserInfoVo.setOpenId(jsonObject.getString("openid"));
        wxPublicNumberUserInfoVo.setUnionId(jsonObject.getString("unionid"));
        wxPublicNumberUserInfoVo.setNickname(jsonObject.getString("nickname"));
        wxPublicNumberUserInfoVo.setSex(jsonObject.getString("sex"));
        wxPublicNumberUserInfoVo.setProvince(jsonObject.getString("province"));
        wxPublicNumberUserInfoVo.setCity(jsonObject.getString("city"));
        wxPublicNumberUserInfoVo.setCountry(jsonObject.getString("country"));

        return wxPublicNumberUserInfoVo;
    }

    /**
     * 组装微信授权返回的数据
     * @param body
     * @return
     */
    private static WxResultInfo buildWxResultInfoData(String body){

        JSONObject jsonObject = JSON.parseObject(body);

        WxResultInfo wxResultInfo = new WxResultInfo();
        wxResultInfo.setOpenId(jsonObject.getString("openid"));
        wxResultInfo.setAccessToken(jsonObject.getString("access_token"));
        wxResultInfo.setExpiresIn(jsonObject.getString("expires_in"));
        wxResultInfo.setSessionKey(jsonObject.getString("session_key"));
        wxResultInfo.setRefreshToken(jsonObject.getString("refresh_token"));
        wxResultInfo.setScope(jsonObject.getString("scope"));
        //满足unionid下发条件的情况下才会返回
        wxResultInfo.setUnionId(jsonObject.getString("unionid"));

        return wxResultInfo;
    }

    /**
     * 微信小程序解析拼装用户数据
     * @param wxUserInfo
     * @return
     */
    private static WxAppUserInfoVo buildWxAppUserInfoData(String wxUserInfo){
        JSONObject jsonObject = JSON.parseObject(wxUserInfo);

        WxAppUserInfoVo wxAppUserInfoVo = new WxAppUserInfoVo();
        wxAppUserInfoVo.setOpenId(jsonObject.getString("openId"));
        wxAppUserInfoVo.setNickName(jsonObject.getString("nickName"));
        wxAppUserInfoVo.setGender(jsonObject.getString("gender"));
        wxAppUserInfoVo.setCity(jsonObject.getString("city"));
        wxAppUserInfoVo.setProvince(jsonObject.getString("province"));
        wxAppUserInfoVo.setCountry(jsonObject.getString("country"));
        wxAppUserInfoVo.setAvatarUrl(jsonObject.getString("avatarUrl"));
        wxAppUserInfoVo.setUnionId(jsonObject.getString("unionId"));
        wxAppUserInfoVo.setWatermark(jsonObject.getString("watermark"));

        return wxAppUserInfoVo;
    }

    /**
     * 设置字符集
     * @param restTemplate
     */
    private static void setRestTemplateCharset(RestTemplate restTemplate){
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     * 验证签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {

        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { WX_PUBLIC_NUMBER_TOKEN, timestamp, nonce };
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        Boolean asd = tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
        return asd;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        return new String(tempArr);
    }

    /**
     * 使用说明
     * @param args
     */
    public static void main(String[] args) {
        //微信公众号
        //获取公众号openid 需要授权码code
        //getWxPublicNumberOpenId("授权码");

        //获取公众号accessToken
        //getWxPublicNumberAccessToken();

        //获取公众号用户信息
        //有两种方式
        //第一种，传参数授权码code
        //getWxPublicNumberUserInfo(code);
        //第二种，传参数openId和accessToken
        //getWxPublicNumberUserInfo(openId,accessToken);


        //微信小程序
        //获取微信小程序openid 需要授权码code
        //getWxAppOpenId(code);

        //获取微信小程序AccessToken
        //getWxAppAccessToken();

        //获取微信小程序用户信息
        //需要参数:
        //encryptedData 微信用户敏感加密数据
        //sessionKey
        //iv 偏移量
        //getWxAppUserInfo();

        //微信公众号消推送
        //wxPublicNumberMessgesPush(openid,"",accessToken,dataMap, null);
    }
}
