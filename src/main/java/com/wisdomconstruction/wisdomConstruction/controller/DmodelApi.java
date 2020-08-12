package com.wisdomconstruction.wisdomConstruction.controller;

import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Api(value = "construction", description = "智慧云工地api",tags = "construction")
@Validated
@RequestMapping("/construction")
public interface DmodelApi {

    @ApiOperation("扬尘设备实时数据")
    @RequestMapping("/dust")
    List<ConstructionSiteDustDTO> dust(String modelType, String programNo);

    @ApiOperation("一周或一月的空气质量")
    @RequestMapping("/airQuality")
    Map<?, ?> airQuality(String projectNo, Integer isInWeek);

    @ApiOperation("测试")
    @RequestMapping("/test")
    void test();

    @ApiOperation("AT指令控制DTU")
    @RequestMapping("/atInstruction")
    String atInstruction(String at, String imei);

    @ApiOperation("地磅指令测试")
    @RequestMapping("/weight")
    String weight();

    @ApiOperation("地磅数据获取")
    @RequestMapping("/getWeight")
    String getWeight(String modelType, String programNo);
}
