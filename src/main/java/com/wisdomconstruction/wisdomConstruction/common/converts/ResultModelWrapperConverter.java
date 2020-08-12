
package com.wisdomconstruction.wisdomConstruction.common.converts;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdomconstruction.wisdomConstruction.common.code.SysCodeMsgEnum;
import com.wisdomconstruction.wisdomConstruction.common.dto.ResultModel;
import com.wisdomconstruction.wisdomConstruction.tool.JacksonTool;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class ResultModelWrapperConverter extends AbstractHttpMessageConverter<Object> {


    /** 系统默认编码 */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public ResultModelWrapperConverter() {
        super(new MediaType("application" , "json" , DEFAULT_CHARSET),
                new MediaType("application" , "*+json" , DEFAULT_CHARSET));

    }
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 重写readlntenal 方法，处理请求的数据。
     */

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage httpInputMessage) throws IOException {
        String bodyText = StreamUtils.copyToString(httpInputMessage.getBody(), StandardCharsets.UTF_8);
        return JacksonTool.parseToObject(bodyText, clazz);
    }


    /**
     * 重写writeInternal ，处理如何输出数据到response。
     */

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException {
        String uri = getRequestURI();
        ResultModel result = getResultModelInstance(obj);
        writeValueWithResultModel(result, outputMessage.getBody());
    }

    private String getRequestURI() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRequestURI();
    }

    private void writeObjectDirectly(Object obj, OutputStream out) throws IOException {
        ObjectMapper mapper = JacksonTool.getSnakeCaseObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(obj);
        out.write(json.getBytes(DEFAULT_CHARSET));
    }



    private ResultModel getResultModelInstance(Object obj) {
        if (obj instanceof ResultModel) {
            return (ResultModel) obj;
        }
        ResultModel result = new ResultModel();
        result.setCode(SysCodeMsgEnum.SUCCESS.getCode());
        result.setMsg(SysCodeMsgEnum.SUCCESS.getMsg());
        result.setData(obj);
        return result;
    }

    private void writeValueWithResultModel(ResultModel model, OutputStream out) throws IOException {
        ObjectMapper mapper = JacksonTool.getSnakeCaseObjectMapper();
//        ObjectMapper mapper = new ObjectMapper();
        out.write(mapper.writeValueAsString(model)
                .getBytes(DEFAULT_CHARSET));
    }

    public static JavaType getCollectionType(ObjectMapper mapper, Class<ResultModel> collectionClass,
                                             Class<?>... elementClasses) {
        return mapper.getTypeFactory()
                .constructParametricType(collectionClass, elementClasses);
    }

}

