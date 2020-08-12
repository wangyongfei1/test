package com.wisdomconstruction.wisdomConstruction.controller.weight;

import com.wisdomconstruction.wisdomConstruction.common.dto.goods.ConstructionGoodsDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(value = "weight", description = "地磅api",tags = "weight")

@RequestMapping("/weight")
public interface WeightApi {

    @ApiOperation("添加称重信息（毛重）")
    @PostMapping("/addWeight")
    boolean addWeight(@RequestBody ConstructionWeightDTO constructionWeightDTO);

    @ApiOperation("更新称重信息（皮重）")
    @PostMapping("/updateWeight")
    boolean updateWeight(@RequestBody ConstructionWeightDTO constructionWeightDTO);

    @ApiOperation("获取地磅当前称重")
    @GetMapping("/getWeight")
    String getWeight(@RequestParam String modelType,@RequestParam String projectNo);

    @ApiOperation("获取地磅称重信息")
    @GetMapping("/getWeights")
    List<ConstructionWeightDTO> getWeights(@RequestParam Integer modelType, @RequestParam String projectNo);

    @ApiOperation("获取当日净重")
    @GetMapping("/getTodayNetWeight")
    float getTodayNetWeight(@RequestParam Integer modelType,@RequestParam String projectNo);

    @ApiOperation("获取当日打单次数")
    @GetMapping("/getTodayBillCount")
    Integer getTodayBillCount(@RequestParam Integer modelType,@RequestParam String projectNo);

    @ApiOperation("获取当日操作员人数")
    @GetMapping("/getTodayOperatorCount")
    Integer getTodayOperatorCount(@RequestParam Integer modelType,@RequestParam String projectNo);

    @ApiOperation("条件查询称重记录")
    @PostMapping("/queryWeightByCondition")
    List<ConstructionWeightDTO> queryWeightByCondition(@RequestBody ConstructionWeightVO constructionWeightVO);

    @ApiOperation("添加货物或者供货商")
    @PostMapping("/addGoods")
    boolean addGoods(@RequestBody ConstructionGoodsDTO goodsDTO);

    @ApiOperation("根据类型查询名称")
    @GetMapping("/selectNameList")
    List<ConstructionGoodsDTO> selectNameList(@RequestParam Integer goodsType);

    @ApiOperation("根据车牌号查询最新的一条称重记录")
    @GetMapping("/selectLastByLicensePlateNo")
    ConstructionWeightDTO selectLastByLicensePlateNo(@RequestParam String licensePlateNo);
}
