package com.wisdomconstruction.wisdomConstruction.dao.base.service.weight.impl;

import com.wisdomconstruction.wisdomConstruction.common.dto.weight.ConstructionWeightDTO;
import com.wisdomconstruction.wisdomConstruction.common.vo.weight.ConstructionWeightVO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.weight.ConstructionWeightDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.weight.ConstructionWeightMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.weight.ConstructionWeightDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地磅设备表 服务实现类
 * </p>
 */
@Service
public class ConstructionWeightDAOImpl extends BaseDAOImpl<ConstructionWeightMapper, ConstructionWeightDO> implements ConstructionWeightDAO {

    @Resource
    private  ConstructionWeightMapper constructionWeightMapper;

    /**
     * 获取当日称重信息
     * @param deviceNo
     * @return
     */
    @Override
    public List<ConstructionWeightDO> getTodayWeight(String deviceNo) {
        return constructionWeightMapper.getTodayWeight(deviceNo);
    }

    /**
     * 获取当日打单次数
     * @param deviceNo
     * @return
     */
    @Override
    public Integer getTodayBillCount(String deviceNo) {
        return constructionWeightMapper.getTodayBillCount(deviceNo);
    }

    /**
     * 根据车牌号查询最新的称重
     * @param licensePlateNo
     * @return
     */
    @Override
    public ConstructionWeightDTO selectLastByLicensePlateNo(String licensePlateNo) {
        return constructionWeightMapper.selectLastByLicensePlateNo(licensePlateNo);
    }

    /**
     * 获取当日操作员人数
     * @param deviceNo
     * @return
     */
    @Override
    public Integer getTodayOperatorCount(String deviceNo) {
        return constructionWeightMapper.getTodayOperatorCount(deviceNo);
    }

    /**
     * 条件查询称重记录
     * @param constructionWeightVO
     * @return
     */
    @Override
    public List<ConstructionWeightDTO> queryWeightByCondition(ConstructionWeightVO constructionWeightVO) {
        return constructionWeightMapper.queryWeightByCondition(constructionWeightVO);
    }

    /**
     * 查询最近的100条称重记录
     * @param deviceNo
     * @return
     */
    @Override
    public List<ConstructionWeightDTO> selectWeightList(String deviceNo) {
        return constructionWeightMapper.selectWeightList(deviceNo);

    }
}