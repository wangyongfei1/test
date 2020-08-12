package com.wisdomconstruction.wisdomConstruction.service.spray;

/**
 * 喷淋
 */
public interface SprayService {

    /**
     * 开阀/关阀
     * @return
     */
    boolean openTheValve( String programNo,Integer status);


    /**
     * 获取阀门开关状态
     */

    Integer getValveStatus(String programNo);
}
