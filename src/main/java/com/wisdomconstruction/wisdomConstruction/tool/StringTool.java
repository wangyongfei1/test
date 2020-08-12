package com.wisdomconstruction.wisdomConstruction.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class StringTool {
    private static final String JSON_REGEX = "\"\\w+\":";

    private StringTool() {
    }

    /**
     * 将字符串转换成驼峰形式
     *
     * @param property 待转换的字符串
     * @return String
     */
    public static String convertToCamelCase(String property) {
        if (property.contains("_")) {
            String[] ns = property.split("_");
            StringBuilder buffer = new StringBuilder();
            for (String n : ns) {
                buffer.append(StringUtils.capitalize(n));
            }
            return StringUtils.uncapitalize(buffer.toString());
        }
        return property;
    }

    /**
     * 将字符串转换成下划线格式
     *
     * @param source 待转换的字符串
     * @return
     */
    public static String convertToSnakeCase(String source) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < source.length(); ++i) {
            char ch = source.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char chCase = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(chCase);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 用给定的Map替换json字符串的key
     *
     * @param inputJSON   待替换的json字符串
     * @param keyRelation key替换关系Map
     * @return String
     */
    public static String replaceJSONKey(String inputJSON, Map<String, String> keyRelation) {
        if (StringUtils.isBlank(inputJSON)) {
            return null;
        }
        if ((keyRelation == null) || keyRelation.isEmpty()) {
            return inputJSON;
        }
        //满足json字符串key的正则
        Pattern pattern = Pattern.compile(JSON_REGEX);
        Matcher matcher = pattern.matcher(inputJSON);
        String result = inputJSON;
        while (matcher.find()) {
            // "username": --> username
            String matcherKey = StringUtils.removeAll(matcher.group(), "\"");
            matcherKey = StringUtils.removeAll(matcherKey, ":");
            if (keyRelation.containsKey(matcherKey)) {
                result = result.replaceAll(matcher.group(), "\"" + keyRelation.get(matcherKey) + "\":");
            }
        }
        return result;
    }

    /**
     * 将json字符串的key转换成驼峰模式的key
     *
     * @param inputJSON 待转换的json字符串
     * @return String
     */
    public static String replaceJSONKeyToCamelCase(String inputJSON) {
        if (StringUtils.isBlank(inputJSON)) {
            return inputJSON;
        }
        //满足json字符串key的正则
        Pattern pattern = Pattern.compile(JSON_REGEX);
        Matcher matcher = pattern.matcher(inputJSON);
        String result = inputJSON;
        while (matcher.find()) {
            result = result.replaceFirst(matcher.group(), StringTool.convertToCamelCase(matcher.group()));
        }
        return result;
    }

    /**
     * 将json字符串的key转换成下划线模式的key
     *
     * @param inputJSON 待转换的json字符串
     * @return String
     */
    public static String replaceJSONKeyToSnakeCase(String inputJSON) {
        if (StringUtils.isBlank(inputJSON)) {
            return inputJSON;
        }
        //满足json字符串key的正则
        Pattern pattern = Pattern.compile(JSON_REGEX);
        Matcher matcher = pattern.matcher(inputJSON);
        String result = inputJSON;
        while (matcher.find()) {
            result = result.replaceFirst(matcher.group(), StringTool.convertToSnakeCase(matcher.group()));
        }
        return result;
    }

    /**
     * 将数值类型的值前缀补零使之达到指定的长度，数值长度超过指定长度时抛出异常
     *
     * @param value  待补零的字符串
     * @param length 指定的长度
     * @return String
     */
    public static String prefixZeroPadding(String value, int length) {
        int valueLength = value.length();
        int diffLength = length - valueLength;
        if (diffLength < 0) {
            log.warn("Length of the string ge the given length,prefix zero padding error.");
            return value;
        }
        if (diffLength == 0) {
            return value;
        }
        StringBuilder sb = new StringBuilder();
        while (diffLength > 0) {
            sb.append("0");
            diffLength--;
        }
        sb.append(value);
        return sb.toString();
    }

    /**
     * 将输入流信息转换为字符串
     *
     * @param inputStream 输入流
     * @return
     */
    public static String getStrFromInputStream(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines()
                .collect(Collectors.joining(
                        System.lineSeparator()));
    }

    /**
     * 将输入的map处理成想要的key然后替换成对应value
     */
    public static String fillContract(String input, Map<String, Object> map) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        for (Map.Entry entry : map.entrySet()) {
            String key = (String) entry.getKey();
            String formatKey = "${" + convertToSnakeCase(key) + "}";
            if ((entry.getValue() == null)) {
                continue;
            }
            input = input.replace(formatKey, String.valueOf(entry.getValue()));
        }
        input = input.replaceAll("\\$\\{[a-z_]+\\}", "");
        return input;
    }

    /**
     * 加法设置待更新金额字段
     *
     * @param column 字段名
     * @param value  增加金额值
     * @return sql片段拼接结果
     */
    public static StringBuilder setAddValueForUpdate(String column, Object value) {
        StringBuilder sb = new StringBuilder();
        return sb.append(column).append(" = ").append(column).append(" + ").append(value.toString());
    }

    /**
     * 根据分割符拼接StringBuilder
     *
     * @param separator 分割符
     * @param elements  StringBuilder集合
     * @return 拼接结果
     */
    public static StringBuilder joinBySeparator(String separator, StringBuilder... elements) {
        return Stream.of(elements).reduce((e1, e2) -> e1.append(separator).append(e2)).orElse(new StringBuilder());
    }

    /**
     * 将字符串自索引index开始位置后的字符串转换成*号
     *
     * @param value      待转换的字符串
     * @param startIndex 开始位置索引
     * @return
     */
    public static String paddingRight(String value, int startIndex) {
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }
        if (value.length() <= startIndex) {
            return value;
        }
        String prefix = StringUtils.left(value, startIndex);
        return StringUtils.rightPad(prefix, StringUtils.length(value), "*");
    }

    /**
     * 将字符串除前几位和后几位的字符转成*号
     *
     * @param value 待转换的字符串
     * @param start 前几位
     * @param end   后几位
     * @return
     */
    public static String paddingAround(String value, int start, int end) {
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }
        if (value.length() <= start + end) {
            return value;
        }
        String prefix = StringUtils.left(value, value.length() - end);
        String suffix = StringUtils.right(value, end);
        return paddingRight(prefix, start).concat(suffix);
    }

    public static String getSerialNo(String... nos) {
        return Arrays.stream(nos)
                .map(no -> StringUtils.isBlank(no) ? "" : no)
                .collect(Collectors.joining("_"));
    }


}
