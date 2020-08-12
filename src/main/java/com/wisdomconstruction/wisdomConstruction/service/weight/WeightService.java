package com.wisdomconstruction.wisdomConstruction.service.weight;


import com.wisdomconstruction.wisdomConstruction.common.dto.goods.ConstructionGoodsDTO;
import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;

import java.util.List;

public interface WeightService {


    /**
     * 获取地磅当前称重
     * @param modelType
     * @param programNo
     * @return
     */
    String getWeight(String modelType, String programNo);

    /**
     * 添加称重信息（毛重）
     * @param constructionWeightDTO
     * @return
     */
    boolean addWeight(ConstructionWeightDTO constructionWeightDTO);

    /**
     * 更新称重信息（皮重）
     * @param constructionWeightDTO
     * @return
     */
    boolean updateWeight(ConstructionWeightDTO constructionWeightDTO);

    /**
     * 获取地磅称重信息
     * @param modelType
     * @param projectNo
     * @return
     */
    List<ConstructionWeightDTO> getWeights(Integer modelType,String projectNo);

    /**
     * 获取当日净重
     * @param modelType
     * @param projectNo
     * @return
     */
    float getTodayNetWeight(Integer modelType, String projectNo);

    /**
     * 获取当日打单次数
     * @param modelType
     * @param projectNo
     * @return
     */
    Integer getTodayBillCount(Integer modelType, String projectNo);

    /**
     * 添加货物或者供货商
     * @param goodsDTO
     * @return
     */
    boolean addGoods(ConstructionGoodsDTO goodsDTO);

    /**
     * 根据类型查询名称
     * @param goodsType
     * @return
     */
    List<ConstructionGoodsDTO> selectNameList(Integer goodsType);

    /**
     * 获取当日操作员人数
     * @param modelType
     * @param projectNo
     * @return
     */
    Integer getTodayOperatorCount(Integer modelType, String projectNo);

    /**
     * 条件查询称重记录
     * @param constructionWeightVO
     * @return
     */
    List<ConstructionWeightDTO> queryWeightByCondition(ConstructionWeightVO constructionWeightVO);

    /**
     * 根据车牌号查询最新的一条称重记录
     * @param licensePlateNo
     * @return
     */
    ConstructionWeightDTO selectLastByLicensePlateNo(String licensePlateNo);
}
