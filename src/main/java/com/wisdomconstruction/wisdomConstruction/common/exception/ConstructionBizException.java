package com.wisdomconstruction.wisdomConstruction.common.exception;

import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.code.SysCodeMsgEnum;
import org.slf4j.LoggerFactory;

public class ConstructionBizException extends ApplicationException{
    public ConstructionBizException(BizCodeMsgEnum bizCodeMsgEnum){
        super(bizCodeMsgEnum.getCode(), bizCodeMsgEnum.getMsg());
    }

    public ConstructionBizException(BizCodeMsgEnum bizCodeMsgEnum, Class<?> zlass) {
        super(bizCodeMsgEnum.getCode(), bizCodeMsgEnum.getMsg());
        LoggerFactory.getLogger(zlass).warn("{}{}" , bizCodeMsgEnum.getCode(), bizCodeMsgEnum.getCode());
    }

    /**
     * 针对业务验证不通过需要业务流程抛出的异常，直接使用错误码和错误信息
     *
     * @param sysCodeMsgEnum 系统错误枚举
     * @param zlass          异常出现的类
     */
    public ConstructionBizException(SysCodeMsgEnum sysCodeMsgEnum, Class<?> zlass) {
        super(sysCodeMsgEnum.getCode(), sysCodeMsgEnum.getMsg());
        LoggerFactory.getLogger(zlass).warn("{}{}" , sysCodeMsgEnum.getCode(), sysCodeMsgEnum.getCode());
    }

    /**
     * 针对业务验证不通过需要业务流程抛出的异常，直接使用错误码和错误信息
     *
     * @param errorCode 错误编码
     * @param errorMsg  错误消息
     * @param zlass     异常出现的类
     */
    public ConstructionBizException(String errorCode, String errorMsg, Class<?> zlass) {
        super(errorCode,errorMsg);
        LoggerFactory.getLogger(zlass).warn("{}{}" , errorCode, errorMsg);
    }

    /**
     * 针对链接错误信息后需要封装抛出的异常，使用包含错误内容的异常（便于日志把异常打印出来）
     *
     * @param cause     异常消息
     * @param errorCode 错误编码
     * @param errorMsg  错误消息
     * @param zlass     异常出现的类
     */
    public ConstructionBizException(Throwable cause, String errorCode, String errorMsg, Class<?> zlass) {
        super(cause, errorCode, errorMsg);
        LoggerFactory.getLogger(zlass).warn("{}{}{}{}" , errorCode, errorMsg, cause.getMessage(), cause);
    }
}
