package com.wisdomconstruction.wisdomConstruction.controller.third;


import com.wisdomconstruction.wisdomConstruction.common.vo.third.ThirdApiVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Api(value = "deviceData", description = "数据推送api",tags = "deviceData")
@Validated
@RequestMapping("/device")
public interface ThirdApi {

    @ApiOperation("数据推送")
    @PostMapping("/pushData")
    String pushData(@Valid @RequestBody ThirdApiVO thirdApiVO);
}
