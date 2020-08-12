package com.wisdomconstruction.wisdomConstruction.controller.highmodulusBasePoint;

import com.wisdomconstruction.wisdomConstruction.common.dto.highmodulusBasePoint.HighModulusBasePointDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "highModulusBasePoint", description = "高支模基准api",tags = "highModulusBasePoint")
@Validated
@RequestMapping("/highModulusBasePoint")
public interface HighModulusBasePointApi {

    @ApiOperation("获取高支模设备基准数据")
    @GetMapping("/getHighModulusBasePoint")
    HighModulusBasePointDTO getHighModulusBasePoint(@RequestParam String serialNumber);

    @ApiOperation("根据项目编号获取高支模设备基准数据")
    @GetMapping("/getHighModulusBasePoints")
    List<HighModulusBasePointDTO> getHighModulusBasePoints(@RequestParam String projectNo);

    @ApiOperation("添加高支模设备基准数据")
    @PostMapping("/addHighModulusBasePoint")
    boolean addHighModulusBasePoint(@RequestBody HighModulusBasePointDTO highModulusBasePointDTO);


    @GetMapping("/updateBasePoint")
    boolean updateBasePoint(@RequestParam String serialNumber);


    @GetMapping("/setBasePoint")
    boolean setBasePoint(@RequestParam String serialNumber);

}
