package com.wisdomconstruction.wisdomConstruction.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * 实体参数验证工具类
 *
 * @auther 20314@etransfar.com(zhangbin)
 * @CreateDate 2018/10/22 11:23
 */
@Component
@Slf4j
public class ValidatorTool {

    private static Validator validator;

    private ValidatorTool() {

    }

    /**
     * 参数校验
     *
     * @param object
     */
    public static boolean validate(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return true;
        }
        Map<String, String> errorMsgMap = new HashMap<>();
        constraintViolations.forEach(constraintViolation -> {
            String[] errorMsg = ConstraintViolationTool.getErrorMsgFromConstraintViolations(constraintViolation);
            errorMsgMap.put(errorMsg[0], errorMsg[1]);
        });
        log.info("Bean validation参数验证失败，失败原因：{}", JacksonTool.parseToJsonString(errorMsgMap));
        return false;
    }

    /**
     * 参数校验并抛出异常
     *
     * @param object
     */
    public static String validateThrowError(Object object) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        List<String> errorList  = new ArrayList<>();
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return "";
        }
        constraintViolations.forEach(
                constraintViolation -> errorList.add(constraintViolation.getMessage()));
        log.info("Bean validation参数验证失败，失败原因：{}", JacksonTool.parseToJsonString(errorList));
        return errorList.get(0);
    }

    @Autowired
    public void setValidator(Validator validator) {
        if (ValidatorTool.validator == null) {
            synchronized (ValidatorTool.class) {
                ValidatorTool.validator = validator;
            }
        }
    }

}
