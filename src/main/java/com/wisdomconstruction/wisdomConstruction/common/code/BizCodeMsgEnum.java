package com.wisdomconstruction.wisdomConstruction.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 业务编码枚举类
 * <p>
 * 后三位定义规则，百分位按照层划分，十分位按组件划分，个位划分具体的错误
 * </p>
 * <p>
 * 0渠道层，1业务层，2核心层，3公共层，4DAO层，5文件操作层，6外部调用层
 * </p>
 * <p>
 * </p>
 */
@Getter
@AllArgsConstructor
public enum BizCodeMsgEnum {


    /**
     * 数据推送
     *
     */
    PUSH_SUCCESS("GP_00", "成功"),
    PUSH_TOKEN_CHECK_FAIL("GP_050002", "无API访问权限"),
    PUSH_DEVICE_TYPE_ERROR("GP_050003", "设备类型不正确"),
    PUSH_DATA_FORMAT_ERROR("GP_050001", "数据参数错误"),
    /**
     * 成功
     */
    SUCCESS("GPBIZ_00", "成功"),
    FAILURE("GPBIZ_050000", "失败"),
    //*******************************渠道层*************************************** //

    //*******************************业务层*************************************** //


    CONSTRUCTION_NO_EXISTS("MSGBIZ_050050", "设备参数不存在"),
    GET_MESSAGE_ERROR("MSGBIZ_050051", "获取数据异常"),
    EQUIPMENT_NO_EXISTS("MSGBIZ_050052", "设备不存在"),


    //*******************************公共层*************************************** //

    TEMPLATE_RENDER_ERROR("GPBIZ_050300", "渲染异常"),
    /**
     * 参数有误
     */
    PARAMETER_ERROR("GPBIZ_050301", "请求参数有误"),
    /**
     * 属性拷贝异常
     */
    BEAN_PROPERTIES_COPY_ERROR("GPBIZ_050302", "属性拷贝异常"),
    /**
     * 数据格式化异常
     */
    DATA_FORMAT_ERROR("GPBIZ_050302", "数据格式化异常"),
    OBJECT_TO_JSON_ERROR("GPBIZ_050303", "对象转换JSON失败"),
    OBJECT_TO_JSON_NODE_ERROR("GPBIZ_050304", "对象转换JsonNode失败"),
    PARSE_JOSN_ERROR("GPBIZ_050305", "解析JSON失败"),
    FILE_PARSE_ERROR("GPBIZ_0503006", "文件解析失败"),
    FILE_GENERATE_ERROR("GPBIZ_0503007", "文件生成失败"),
    SIGN_ERROR("GPBIZ_050308", "加签验签失败"),
    CIRCULAR_BEAN_ERROR("GPBIZ_050309", "bean循环引用错误"),
    CLASS_MATCH_ERROR("GPBIZ_050310", "类匹配错误"),
    PARAMETER_SIGN_ERROR("GPBIZ_050311", "请求参数加签异常"),

    REDIS_KEY_ERROR("GPBIZ_050350", "redis-key生成异常"),

    //*******************************DAO层************************************** //
    /**
     * 数据库异常
     */
    DB_ERROR("GPBIZ_050400", "数据库异常"),
    /**
     * 数据异常，不存在或者状态等异常
     */
    DATA_ERROR("GPBIZ_050401", "数据异常。"),
    DATA_EXISTS_ERROR("GPBIZ_050402", "数据已经存在"),
    DATA_REPEAT_SUBMIT("GPBIZ_050403", "重复提交"),
    /**
     * 数据不存在
     */
    DATA_NOT_FOUND("GPBIZ_050004", "数据不存在"),
    /**
     * 插入数据失败
     */
    INSERT_FAILURE("GPBIZ_050105", "数据插入失败"),
    /**
     * 状态异常
     */
    ERROR_STATUS("GPBIZ_050408", "状态异常。"),
    /**
     * 没有更新到数据
     */
    UPDATE_ZERO_ROW_ERROR("GPBIZ_050409", "没有更新到数据"),

    //*******************************文件操作层********************************** //


    //*******************************外部调用层********************************** //
    HTTP_CALL_TIME_OUT_ERROR("GPBIZ_060000", "接口调用超时"),
    HTTP_CALL_ERROR("GPBIZ_060001", "接口调用错误"),
    HTTP_CALL_UNKNOWN_ERROR("GPBIZ_060002", "未知异常"),
    HTTP_RESPONSE_ERROR("GPBIZ_060003", "接口请求返回失败"),


    /**
     * api(三方)
     */
    PLATFORM_NOT_FOUND("GPBIZ_060100", "平台信息不存在"),
    API_CONFIG_NOT_FOUND("GPBIZ_060100", "接口配置不存在");

    /**
     * 编码CODE
     */
    private String code;
    /**
     * 编码MSG
     */
    private String msg;
}
