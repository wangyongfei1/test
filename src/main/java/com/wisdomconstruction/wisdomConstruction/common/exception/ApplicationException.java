package com.wisdomconstruction.wisdomConstruction.common.exception;

/**
 * 全局异常类
 * @author qiupengkang
 * @date 2020/4/15 13:30
 */
public class ApplicationException extends RuntimeException{

    private final String errorCode;
    private final String errorMsg;

    public ApplicationException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, String errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, Throwable cause, String errorCode, String errorMsg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(Throwable cause, String errorCode, String errorMsg) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public String toString() {
        return "ApplicationException{errorCode=" + this.errorCode + ", errorMsg='" + this.errorMsg + '\'' + ", traceMessage='" + this.getMessage() + '\'' + '}';
    }
}
