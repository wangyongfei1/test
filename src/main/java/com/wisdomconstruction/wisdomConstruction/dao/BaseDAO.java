/*------------------------------------------------------------------------------
 * COPYRIGHT Transfar Pay 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Transfar Pay Inc. The programs may be used and/or copied only with written
 * permission from Transfar Pay Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.wisdomconstruction.wisdomConstruction.dao;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 服务层操作基础接口
 *
 * @param <T>
 */
public interface BaseDAO<T extends BaseBizModel> extends IService<T> {

    /**
     * 按照给定的查询大小查询列表，不给size值默认查询200条数据
     * @param size 查询列表的大小
     * @param wrapper 查询条件
     * @return
     */
    List<T> selectList(Integer size, Wrapper<T> wrapper);


    /**
     * 分页查询，分页大小不设限
     *
     * @param page
     * @param wrapper
     * @return
     */
    Page<T> selectPageUnlimited(Page<T> page, Wrapper<T> wrapper);

    /**
     * 传入一个entitylist,将每一个entity作为一个查询条件然后用or连接
     * @param size
     * @param entities
     * @return
     */
    Page<T> selectPageWithAll(Integer size, List<T> entities, Integer current);

    /**
     * 条件查找列表
     * 该方法用途是：通过xml文件的SQL执行分页查找
     *
     * @param page 分页信息
     * @param t 查询条件
     * @return
     */
    Page<T> selectListByCondition(Page<T> page, T t);

}
