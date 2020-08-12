package com.wisdomconstruction.wisdomConstruction.controller.third.Impl;

import com.wisdomconstruction.wisdomConstruction.common.vo.third.ThirdApiVO;
import com.wisdomconstruction.wisdomConstruction.controller.third.ThirdApi;
import com.wisdomconstruction.wisdomConstruction.service.third.ThirdApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class ThirdController implements ThirdApi {

    @Autowired
    private ThirdApiService thirdApiService;
    /**
     * 数据推送
     * @param thirdApiVO
     * @return
     */
    @Override
    public String pushData(@Valid @RequestBody ThirdApiVO thirdApiVO) {
        return thirdApiService.pushData(thirdApiVO);
    }


}
