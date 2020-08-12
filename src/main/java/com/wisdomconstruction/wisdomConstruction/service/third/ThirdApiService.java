package com.wisdomconstruction.wisdomConstruction.service.third;

import com.wisdomconstruction.wisdomConstruction.common.vo.third.ThirdApiVO;

public interface ThirdApiService {
    /**
     * 数据推送
     * @param thirdApiVO
     * @return
     */
    String pushData(ThirdApiVO thirdApiVO);
}
