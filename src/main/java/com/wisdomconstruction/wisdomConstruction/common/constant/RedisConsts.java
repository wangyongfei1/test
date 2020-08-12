package com.wisdomconstruction.wisdomConstruction.common.constant;

/**
 * @author kongke
 * @version 1.0
 * @intention
 * @date 2020/5/7 14:40
 */
public interface RedisConsts {

    String weekAirQuality = "CONSTR:DUST:WAQI:{projectNo}";

    String monthAirQuality = "CONSTR:DUST:MAQI:{projectNo}";

    String weekAirData = "CONSTR:DUST:WDATA:{projectNo}";
}
