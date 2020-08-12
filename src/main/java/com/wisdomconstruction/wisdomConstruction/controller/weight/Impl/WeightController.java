package com.wisdomconstruction.wisdomConstruction.controller.weight.Impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.goods.ConstructionGoodsDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;
import com.wisdomconstruction.wisdomConstruction.controller.weight.WeightApi;
import com.wisdomconstruction.wisdomConstruction.service.weight.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WeightController implements WeightApi {

    @Autowired
    private WeightService weightService;

    /**
     * 添加称重信息（毛重）
     * @param constructionWeightDTO
     * @return
     */
    public boolean addWeight(@RequestBody ConstructionWeightDTO constructionWeightDTO){
        return weightService.addWeight(constructionWeightDTO);
    }

    /**
     * 更新称重信息（皮重）
     * @param constructionWeightDTO
     * @return
     */
    public boolean updateWeight(@RequestBody ConstructionWeightDTO constructionWeightDTO){
        return weightService.updateWeight(constructionWeightDTO);
    }

    /**
     * 获取地磅当前称重
     * @param modelType
     * @param projectNo
     * @return
     */
    public String getWeight(@RequestParam String modelType,@RequestParam String projectNo){
        return weightService.getWeight(modelType,projectNo);
    }

    /**
     * 获取地磅称重信息
     * @param modelType
     * @param projectNo
     * @return
     */
    public List<ConstructionWeightDTO> getWeights(@RequestParam Integer modelType, @RequestParam String projectNo){
        return weightService.getWeights(modelType,projectNo);
    }

    /**
     * 获取当日净重
     * @param modelType
     * @param projectNo
     * @return
     */
    public float getTodayNetWeight(@RequestParam Integer modelType, @RequestParam String projectNo){
        return weightService.getTodayNetWeight(modelType,projectNo);
    }

    /**
     * 获取当日打单次数
     * @param modelType
     * @param projectNo
     * @return
     */
    public Integer getTodayBillCount(@RequestParam Integer modelType,@RequestParam String projectNo){
        return weightService.getTodayBillCount(modelType,projectNo);
    }
    /**
     * 获取当日操作员人数
     * @param modelType
     * @param projectNo
     * @return
     */
    public Integer getTodayOperatorCount(@RequestParam Integer modelType,@RequestParam String projectNo){
        return weightService.getTodayOperatorCount(modelType,projectNo);
    }

    /**
     * 条件查询称重记录
     * @param constructionWeightVO
     * @return
     */
    public List<ConstructionWeightDTO> queryWeightByCondition(@RequestBody ConstructionWeightVO constructionWeightVO){
        return weightService.queryWeightByCondition(constructionWeightVO);
    }

    /**
     * 添加货物或者供货商
     * @param goodsDTO
     * @return
     */
    public boolean addGoods(@RequestBody ConstructionGoodsDTO goodsDTO){
        return weightService.addGoods(goodsDTO);
    }

    /**
     * 根据类型查询名称
     * @param goodsType 0：货名  1：供货商
     * @return
     */
    public List<ConstructionGoodsDTO> selectNameList(@RequestParam Integer goodsType){
        return weightService.selectNameList(goodsType);
    }

    /**
     * 根据车牌号查询最新的一条称重记录
     * @param licensePlateNo
     * @return
     */
    public ConstructionWeightDTO selectLastByLicensePlateNo(String licensePlateNo){
        return weightService.selectLastByLicensePlateNo(licensePlateNo);
    }

}