package com.wisdomconstruction.wisdomConstruction.dao.base.service.wxuser;

import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.base.domain.wxuser.WxUserInfoDO;

/**
 * @author wangyongfei
 * @date 2020/7/9 18:41
 */
public interface WxUserInfoDAO extends BaseDAO<WxUserInfoDO> {

    WxUserInfoDO getWxUserInfo(String unionId);

}