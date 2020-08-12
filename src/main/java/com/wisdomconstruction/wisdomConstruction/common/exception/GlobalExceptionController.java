
package com.wisdomconstruction.wisdomConstruction.common.exception;

import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.code.SysCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.dto.ResultModel;
import com.wisdomconstruction.wisdomConstruction.tool.ConstraintViolationTool;
import com.wisdomconstruction.wisdomConstruction.tool.StringTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionController extends AbstractErrorController {


    /**
     * 构造函数
     *
     * @param errorAttributes
     */
    public GlobalExceptionController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 处理全局的/error请求
     *
     * @param request
     * @return
     */
    @RequestMapping
    public ResultModel error(HttpServletRequest request, Exception e) {
        // 参考org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter第79行
        Object exception = request.getAttribute("javax.servlet.error.exception" );
        if (exception != null) {
            return handleException(request, (Exception) exception);
        }
        Object errCode = request.getAttribute("javax.servlet.error.status_code" );
        if ((errCode != null) && (errCode.toString().length() > 0)) {
            Object requestUri = request.getAttribute("javax.servlet.error.request_uri" );
            if (requestUri != null) {
                return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(),
                        SysCodeMsgEnum.SYS_ERROR.getMsg());
            }
        }
        Object msg = request.getAttribute("javax.servlet.error.message" );
        return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(), msg.toString());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleBindException(HttpServletRequest request, BindException e) {
        log.warn("参数绑定错误，请求地址：{}，错误信息：{}。" , request.getRequestURI(), e.toString(), e);
        return ResultModel.renderError(SysCodeMsgEnum.PARAMETERS_BIND_ERROR.getCode(),
                getErrorMsgFromFieldError(e.getFieldErrors()));
    }

    /**
     * @param
     * @param
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultModel HttpMessageNotReadableException(HttpServletRequest httpServletRequest) {
        //log.warn("参数绑定错误，请求地址：{}，错误信息：{}。" , request.getRequestURI(), e.toString(), e);
        return ResultModel.renderError(SysCodeMsgEnum.BODY_FORMAT_ERROR.getCode(),
                SysCodeMsgEnum.BODY_FORMAT_ERROR.getMsg());
    }

    /**
     * @param
     * @param
     * @return
     */
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public ResultModel HttpRequestMethodNotSupportedException(HttpServletRequest httpServletRequest) {
        //log.warn("参数绑定错误，请求地址：{}，错误信息：{}。" , request.getRequestURI(), e.toString(), e);
        return ResultModel.renderError(SysCodeMsgEnum.REQUEST_METHOD_ERROR.getCode(),
                SysCodeMsgEnum.REQUEST_METHOD_ERROR.getMsg());
    }
    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel laborBizException(HttpServletRequest request, Exception e) {
        log.warn("自定义错误，请求地址：{}，错误信息：{}。" , request.getRequestURI(), e.toString(), e);
        ApplicationException applicationException = (ApplicationException) e;
        return ResultModel.renderError(applicationException.getErrorCode(),applicationException.getErrorMsg());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleConstraintViolationException(HttpServletRequest request,
                                                          ConstraintViolationException e) {
        log.warn("参数验证错误，请求地址：{}，错误信息：{}" , request.getRequestURI(), e.toString(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        ConstraintViolation<?> constraintViolation = constraintViolations.iterator().next();
        String[] errorMsg = ConstraintViolationTool.getErrorMsgFromConstraintViolations(constraintViolation);
        return ResultModel.renderError(SysCodeMsgEnum.PARAMETERS_VALID_ERROR.getCode(),
                errorMsg[1]);
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleMethodNotValidException(HttpServletRequest request,
                                                     MethodArgumentNotValidException e) {
        log.warn("参数验证错误，请求地址：{}，错误信息：{} " , request.getRequestURI(), e.toString(), e);
        BindingResult bindingResult = e.getBindingResult();
        return ResultModel.renderError(SysCodeMsgEnum.PARAMETERS_BIND_ERROR.getCode(),
                getErrorMsgFromFieldError(bindingResult.getFieldErrors()));
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleRequestParameterException(HttpServletRequest request,
                                                       MissingServletRequestParameterException e) {
        log.warn("请求参数不存在，请求地址：{}，错误信息：{} " , request.getRequestURI(), e.toString(), request.getRequestURI());
        return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(), e.getMessage());
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleRequestParameterMismatchException(HttpServletRequest request,
                                                               MethodArgumentTypeMismatchException e) {
        log.warn("参数转换错误，请求地址：{}，错误信息：{} " , request.getRequestURI(), e.toString(), e);
        String errMsg = StringTool.convertToSnakeCase(e.getName()) + " required type should be " + e.getRequiredType();
        return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(), errMsg);
    }

    /**
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResultModel handleException(HttpServletRequest request, Exception e) {
        log.warn("未知异常错误，请求地址：{}，错误信息：{} " , request.getRequestURI(), e.toString(), e);
        if (e instanceof NullPointerException) {
            log.warn(String.format("npe exception. request uri: %s" , request.getRequestURI()));
            return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(), SysCodeMsgEnum.SYS_ERROR.getMsg());
        } else if (e instanceof NumberFormatException) {
            return ResultModel.renderError(SysCodeMsgEnum.NUMBER_FORMAT_ERROR.getCode(),
                    SysCodeMsgEnum.NUMBER_FORMAT_ERROR.getMsg());
        } else if (e instanceof IllegalArgumentException) {
            // 参数异常
            return ResultModel.renderError(BizCodeMsgEnum.PARAMETER_ERROR.getCode(),
                    e.getMessage());
        } else if (e instanceof ClassCastException) {
            // 类型转换异常
            return ResultModel.renderError(SysCodeMsgEnum.CLASS_CAST_ERROR.getCode(),
                    SysCodeMsgEnum.CLASS_CAST_ERROR.getMsg());
        }
        log.warn(String.format("unchecked exception. request uri: %s" , request.getRequestURI()), e);
        return ResultModel.renderError(SysCodeMsgEnum.SYS_ERROR.getCode(), SysCodeMsgEnum.SYS_ERROR.getMsg());
    }


    /**
     * 根据字段错误返回具体错误信息
     *
     * @param fieldErrors 字段错误列表信息
     * @return
     */
    private String getErrorMsgFromFieldError(List<FieldError> fieldErrors) {
        //Map<String, String> errorMsgMap = new HashMap<>();
        /*fieldErrors.forEach(objectError -> errorMsgMap.put(StringTool.convertToSnakeCase(objectError.getField()),
                objectError.getDefaultMessage()));*/
        return fieldErrors.get(0).getDefaultMessage();
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
