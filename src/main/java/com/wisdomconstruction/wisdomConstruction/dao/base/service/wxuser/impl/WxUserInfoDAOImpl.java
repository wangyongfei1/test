package com.wisdomconstruction.wisdomConstruction.dao.base.service.wxuser.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.wxuser.WxUserInfoDO;
import com.wisdomconstruction.wisdomConstruction.dao.base.enums.wxuser.WxUserInfoDOPropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.wxuser.WxUserInfoMapper;
import com.wisdomconstruction.wisdomConstruction.dao.base.service.wxuser.WxUserInfoDAO;
import org.springframework.stereotype.Repository;

/**
 * @author wangyongfei
 * @date 2020/7/9 18:48
 */
@Repository("wxUserInfoDAO")
public class WxUserInfoDAOImpl extends BaseDAOImpl<WxUserInfoMapper, WxUserInfoDO> implements WxUserInfoDAO {
    @Override
    public WxUserInfoDO getWxUserInfo(String unionId) {
        return selectOne(new EntityWrapper<WxUserInfoDO>().
                eq(WxUserInfoDOPropertiesEnum.UNION_ID.getColumn(),unionId));
    }
}