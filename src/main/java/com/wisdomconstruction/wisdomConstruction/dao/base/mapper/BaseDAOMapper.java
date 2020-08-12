/*------------------------------------------------------------------------------
 * COPYRIGHT Transfar Pay 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Transfar Pay Inc. The programs may be used and/or copied only with written
 * permission from Transfar Pay Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.wisdomconstruction.wisdomConstruction.dao.base.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;

import java.util.List;

/**
 * 数据库操作基础接口 BaseDAOMapper
 *
 * @param <T>
 */
public interface BaseDAOMapper<T extends BaseBizModel> extends BaseMapper<T> {

    /**
     * 条件查找列表
     * 该方法用途是：通过xml文件的SQL执行分页查找
     *
     * @param page 分页信息
     * @param t    查询条件
     * @return
     */
    List<T> selectListByCondition(Page<T> page, T t);

}
