package com.wisdomconstruction.wisdomConstruction.controller.dustthreshold;

import com.wisdomconstruction.wisdomConstruction.common.dto.dustthreshold.DustThresholdDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author wangyongfei
 * @date 2020/6/29 20:27
 */
@Api(tags = "dustThreshold")
@Validated
@RequestMapping("dustThreshold")
public interface DustThresholdApi {

    @ApiOperation("更新扬尘阈值")
    @PostMapping("updateDustThreshold")
    boolean updateDustThreshold(@Valid DustThresholdDTO dustThresholdDTO);

    @ApiOperation("查询扬尘阈值")
    @GetMapping("getDustThreshold")
    DustThresholdDTO getDustThreshold(@NotBlank(message = "项目编号不能为空") String projectNo);
}