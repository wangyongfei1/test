package com.wisdomconstruction.wisdomConstruction.controller.intelligentspray;

import com.wisdomconstruction.wisdomConstruction.common.dto.intelligentspray.IntelligentSprayDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 智能喷淋 Api
 * @author wangyongfei
 * @date 2020/7/4 10:05
 */
@Api(tags = "intelligentSpray")
@RequestMapping("/intelligentSpray")
@Validated
public interface IntelligentSprayApi {

    @ApiOperation("获取开关状态")
    @GetMapping("/getSwitchStatus")
    IntelligentSprayDTO getSwitchStatus(@NotBlank(message = "项目编号不能为空") String projectNo);

    @ApiOperation("改变总开关状态")
    @GetMapping("/changeMainSwitch")
    boolean changeMainSwitch(@NotBlank(message = "项目编号不能为空") String projectNo,
                              @NotNull(message = "开关状态不能为空") boolean status);

    @ApiOperation("改变喷淋开关状态")
    @GetMapping("/changeSpraySwitch")
    boolean changeSpraySwitch(@NotBlank(message = "项目编号不能为空") String projectNo,
                             @NotNull(message = "开关状态不能为空") boolean status);

}