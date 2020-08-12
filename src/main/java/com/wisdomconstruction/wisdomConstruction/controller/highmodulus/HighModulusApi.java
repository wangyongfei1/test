package com.wisdomconstruction.wisdomConstruction.controller.highmodulus;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulus.ConstructionHighModulusDTO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.highmodulus.ConstructionHighModulusDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Api(value = "highModulus", description = "高支模api",tags = "highModulus")
@Validated
@RequestMapping("/highModulus")
public interface HighModulusApi {

    @ApiOperation("获取高支模设备实时数据")
    @GetMapping("/getHighModulus")
    List<ConstructionHighModulusDTO> getHighModulus(@RequestParam String serialNumber);

    @ApiOperation("获取高支模设备报警实时数据")
    @GetMapping("/getAlertHighModulus")
    List<ConstructionHighModulusDTO> getAlertHighModulus(@RequestParam String serialNumber);

    @ApiOperation("获取当天报警次数")
    @GetMapping("/getAlertCount")
    Integer getAlertCount(@RequestParam String serialNumber);

}
