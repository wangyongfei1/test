package com.wisdomconstruction.wisdomConstruction.tool;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JacksonSnakeCaseStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {

    /**
     * 将字符串转换为下划线的形式，针对徙木特殊处理，数字前转换为下划线
     * @param propertyName
     * @return
     */
    @Override
    public String translate(String propertyName) {
        StringBuilder buf = new StringBuilder();
        boolean preCaseIsNumber = false;
        for (int i = 0; i < propertyName.length(); ++i) {
            char ch = propertyName.charAt(i);
            boolean isUpperCase = ch >= 'A' && ch <= 'Z';
            boolean isNumber = ch >= '0' && ch <= '9';
            if (isUpperCase) {
                char chUcase = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(chUcase);
                preCaseIsNumber = false;
            } else if (isNumber){
                if (!preCaseIsNumber && i > 0) {
                    buf.append('_');
                    preCaseIsNumber = true;
                }
                buf.append(ch);
            } else {
                buf.append(ch);
                preCaseIsNumber = false;
            }
        }
        return buf.toString();
    }
}
