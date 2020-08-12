package com.wisdomconstruction.wisdomConstruction.common.dto;

import com.wisdomconstruction.wisdomConstruction.common.code.BizCodeMsgEnum;
import lombok.Data;

@Data
public class ResultModel {
    
    /**
     * 返回编码
     */
    String code;

    /**
     * 返回信息
     */
    String msg;

    /**
     * 返回数据
     */
    Object data;

    public static ResultModel renderError(String bizCode, String bizMsg) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(bizCode);
        resultModel.setMsg(bizMsg);
        return resultModel;
    }

    public static ResultModel renderSccesss(BizCodeMsgEnum bizCodeMsgEnum) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(bizCodeMsgEnum.getCode());
        resultModel.setMsg(bizCodeMsgEnum.getMsg());
        return resultModel;
    }

    public static ResultModel renderError(BizCodeMsgEnum bizCodeMsgEnum) {
        ResultModel resultModel = new ResultModel();
        resultModel.setCode(bizCodeMsgEnum.getCode());
        resultModel.setMsg(bizCodeMsgEnum.getMsg());
        return resultModel;
    }
}
