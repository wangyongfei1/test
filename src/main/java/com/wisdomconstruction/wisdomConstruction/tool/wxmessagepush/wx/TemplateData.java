package com.wisdomconstruction.wisdomConstruction.tool.wxmessagepush.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: liujl
 * @DateTime: 2020-06-22 16:52
 * @Description: 用来定义消息的内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateData {

    //消息内容
    private String value;
}
