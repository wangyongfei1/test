package com.wisdomconstruction.wisdomConstruction.controller.equipment;

import com.wisdomconstruction.wisdomConstruction.common.dto.constructionEquipment.ConstructionEquipmentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Api(value = "constructionEquipment", description = "设备api",tags = "constructionEquipment")
@Validated
@RequestMapping("/constructionEquipment")
public interface EquipmentApi {

    @ApiOperation("根据设备类型获取设备")
    @GetMapping("/getEquipment")
    List<ConstructionEquipmentDTO> getEquipment(@RequestParam String deviceType, @RequestParam String projectNo);
}
