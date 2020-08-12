package com.wisdomconstruction.wisdomConstruction.service.dustthreshold;

import com.wisdomconstruction.wisdomConstruction.common.dto.dustthreshold.DustThresholdDTO;

/**
 * @author wangyongfei
 * @date 2020/6/29 20:34
 */
public interface DustThresholdService {

    /**
     * 更新扬尘阈值
     * @param dustThresholdDTO
     * @return
     */
    boolean updateDustThreshold(DustThresholdDTO dustThresholdDTO);

    /**
     * 查询扬尘阈值
     * @param projectNo
     * @return
     */
    DustThresholdDTO getDustThreshold(String projectNo);
}