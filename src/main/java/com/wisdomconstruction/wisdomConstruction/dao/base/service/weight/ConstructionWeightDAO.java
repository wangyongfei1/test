package com.wisdomconstruction.wisdomConstruction.dao.base.service.weight;

import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;
import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.weight.ConstructionWeightDO;

import java.util.List;

public interface ConstructionWeightDAO extends BaseDAO<ConstructionWeightDO> {

    /**
     * 获取当日称重信息
     * @param deviceNo
     * @return
     */
    List<ConstructionWeightDO> getTodayWeight(String deviceNo);

    /**
     * 获取当日打单次数
     * @param deviceNo
     * @return
     */
    Integer getTodayBillCount(String deviceNo);

    /**
     * 根据车牌号查询最新的称重
     * @param licensePlateNo
     * @return
     */
    ConstructionWeightDTO selectLastByLicensePlateNo(String licensePlateNo);

    /**
     * 获取当日操作员人数
     * @param deviceNo
     * @return
     */
    Integer getTodayOperatorCount(String deviceNo);

    /**
     * 条件查询称重记录
     * @param constructionWeightVO
     * @return
     */
    List<ConstructionWeightDTO> queryWeightByCondition(ConstructionWeightVO constructionWeightVO);

    /**
     * 查询最近的100条称重记录
     * @param deviceNo  设备号
     * @return
     */
    List<ConstructionWeightDTO> selectWeightList(String deviceNo);
}