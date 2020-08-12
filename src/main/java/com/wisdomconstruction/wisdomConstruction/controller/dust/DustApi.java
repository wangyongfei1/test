package com.wisdomconstruction.wisdomConstruction.controller.dust;

import com.wisdomconstruction.wisdomConstruction.common.dto.dust.ConstructionSiteDustDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Api(value = "dust", description = "扬尘api",tags = "dust")
@Validated
@RequestMapping("/dust")
public interface DustApi {

    @ApiOperation("扬尘设备实时数据")
    @GetMapping("/dusts")
    List<ConstructionSiteDustDTO> dust(@RequestParam String modelType, @RequestParam String programNo);

    @ApiOperation("近七天的空气质量")
    @GetMapping("/listAirQuality")
    Map<?, ?> listAirQuality(@RequestParam String projectNo);

    @ApiOperation("近七天的扬尘数据展示")
    @GetMapping("/renderDust")
    ConstructionSiteDustDTO renderDust(@RequestParam String projectNo);
}
