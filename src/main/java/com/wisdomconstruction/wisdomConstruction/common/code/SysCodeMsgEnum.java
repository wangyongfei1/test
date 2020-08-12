package com.wisdomconstruction.wisdomConstruction.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SysCodeMsgEnum {
    /**
     * 成功
     */
    SUCCESS("GP_00" , "成功" ),
    /**
     * 系统正忙
     */
    SYS_BUSY("GP_050999" , "系统正忙" ),
    /**
     * 系统异常
     */
    SYS_ERROR("GP_050998" , "系统异常" ),
    /**
     * 您没有该接口服务的权限
     */
    PERMISSION_DENY("GP_050002" , "您没有该接口服务的权限" ),
    /** 时间戳不正确业务请求超时 */
    //TIMESTAMP_ERROR("GP_050004", "时间戳不正确业务请求超时"), //should decide valid or not
    /**
     * 参数绑定异常
     */
    PARAMETERS_BIND_ERROR("GP_050006" , "参数绑定错误" ),
    /**
     * 参数校验错误
     */
    PARAMETERS_VALID_ERROR("GP_050007" , "参数校验错误" ),
    /**
     * 类型转换错误
     */
    CLASS_CAST_ERROR("GP_050008" , "类型转换错误" ),
    /**
     * 类型转换错误
     */
    NUMBER_FORMAT_ERROR("GP_050009" , "数值类型转换错误" ),
    BODY_FORMAT_ERROR("GP_050010" , "请求格式错误" ),
    REQUEST_METHOD_ERROR("GP_050011" , "请求方式错误" ),
    ;

    /**
     * 编码CODE
     */
    private String code;
    /**
     * 编码MSG
     */
    private String msg;
}
