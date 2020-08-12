package com.wisdomconstruction.wisdomConstruction.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

/**
 * 参数校验结果处理工具类
 *
 */
@Component
public class ConstraintViolationTool {

    private ConstraintViolationTool() {

    }

    private static ParameterNameDiscoverer parameterNameDiscoverer;

    @Autowired
    public void setParameterNameDiscoverer(ParameterNameDiscoverer parameterNameDiscoverer) {
        if (ConstraintViolationTool.parameterNameDiscoverer == null) {
            synchronized (ConstraintViolationTool.class) {
                ConstraintViolationTool.parameterNameDiscoverer = parameterNameDiscoverer;
            }
        }
    }

    /**
     * 根据ConstraintViolation对象定制具体的返回错误信息
     * @param constraintViolation
     * @return
     */
    public static String[] getErrorMsgFromConstraintViolations(ConstraintViolation constraintViolation) {
        String[] errorMsg = new String[2];
        String pathStr = constraintViolation.getPropertyPath().toString();
        if (pathStr.contains(".") && (constraintViolation.getRootBean() == constraintViolation.getLeafBean())) {
            errorMsg[0] = StringTool.convertToSnakeCase(getParameterNameFromClass(constraintViolation));
            errorMsg[1] = constraintViolation.getMessage();
        } else {
            errorMsg[0] = StringTool.convertToSnakeCase(getParameterName(pathStr));
            errorMsg[1] = constraintViolation.getMessage();
        }
        return errorMsg;
    }

    /**
     * 使用Class通过反射获取参数名称
     * @param constraintViolation
     * @return
     */
    private static String getParameterNameFromClass(ConstraintViolation constraintViolation) {
        Class cls = constraintViolation.getRootBeanClass();
        Method[] methods = cls.getMethods();
        Iterator<Path.Node> propertyPath = constraintViolation.getPropertyPath().iterator();
        Path.MethodNode methodNode = propertyPath.next().as(Path.MethodNode.class);
        Path.ParameterNode parameterNode = propertyPath.next().as(Path.ParameterNode.class);
        Optional<String> parameterNameOptional = Arrays.stream(methods)
                .filter(method -> method.getName().equals(methodNode.getName())).map(method -> {
                    String[] names = parameterNameDiscoverer.getParameterNames(method);
                    return names[parameterNode.getParameterIndex()];
                }).findFirst();
        return parameterNameOptional.isPresent() ? parameterNameOptional.get() : null;
    }

    /**
     * 从propertyPath获取参数名称
     * @param pathStr
     * @return
     */
    private static String getParameterName(String pathStr) {
        Optional<String> parameterNameOptional = Optional.of(pathStr).flatMap(s -> {
            if (s.contains(".")) {
                return Optional.of(s.substring(s.lastIndexOf('.') + 1));
            } else {
                return Optional.of(s);
            }
        });
        return parameterNameOptional.isPresent() ? parameterNameOptional.get() : null;
    }

}
