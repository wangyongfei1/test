/*------------------------------------------------------------------------------
 * COPYRIGHT Transfar Pay 2018
 *
 * The copyright to the computer program(s) herein is the property of
 * Transfar Pay Inc. The programs may be used and/or copied only with written
 * permission from Transfar Pay Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *----------------------------------------------------------------------------*/
package com.wisdomconstruction.wisdomConstruction.dao.base.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisdomconstruction.wisdomConstruction.dao.BaseBizModel;
import com.wisdomconstruction.wisdomConstruction.dao.BaseDAO;
import com.wisdomconstruction.wisdomConstruction.dao.BasePropertiesEnum;
import com.wisdomconstruction.wisdomConstruction.dao.IsDeleteEnum;
import com.wisdomconstruction.wisdomConstruction.dao.base.*;
import com.wisdomconstruction.wisdomConstruction.dao.base.mapper.BaseDAOMapper;
import com.wisdomconstruction.wisdomConstruction.tool.JacksonTool;
import org.springframework.util.Assert;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务层操作基础接口实现类 BaseDAOImpl
 *
 * @param <M>
 * @param <T>
 */
public class BaseDAOImpl<M extends BaseDAOMapper<T>, T extends BaseBizModel> extends ServiceImpl<M, T>
        implements BaseDAO<T> {
    private static final String WRAPPER_MSG = "查询条件不能伟为空。";
    private static final int DEFAULT_PAGE_SIZE = 200;

    @Override
    public boolean insert(T entity) {
        try {
            return super.insert(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertAllColumn(T entity) {
        try {
            return super.insertAllColumn(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertBatch(List<T> entityList) {
        try {
            return super.insertBatch(entityList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertBatch(List<T> entityList, int batchSize) {
        try {
            return super.insertBatch(entityList, batchSize);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdate(T entity) {
        try {
            return super.insertOrUpdate(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdateAllColumn(T entity) {
        try {
            return super.insertOrUpdateAllColumn(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdateBatch(List<T> entityList) {
        try {
            return super.insertOrUpdateBatch(entityList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdateBatch(List<T> entityList, int batchSize) {
        try {
            return super.insertOrUpdateBatch(entityList, batchSize);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<T> entityList) {
        try {
            return super.insertOrUpdateAllColumnBatch(entityList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean insertOrUpdateAllColumnBatch(List<T> entityList, int batchSize) {
        try {
            return super.insertOrUpdateAllColumnBatch(entityList, batchSize);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean deleteById(Serializable id) {
        try {
            return super.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean deleteByMap(Map<String, Object> columnMap) {
        try {
            return super.deleteByMap(columnMap);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean delete(Wrapper<T> wrapper) {
        try {
            return super.delete(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        try {
            return super.deleteBatchIds(idList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateById(T entity) {
        try {
            return super.updateById(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateAllColumnById(T entity) {
        try {
            return super.updateAllColumnById(entity);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean update(T entity, Wrapper<T> wrapper) {
        try {
            return super.update(entity, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public boolean updateForSet(String setStr, Wrapper<T> wrapper) {
        try {
            return super.updateForSet(setStr, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateBatchById(List<T> entityList) {
        try {
            return super.updateBatchById(entityList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateBatchById(List<T> entityList, int batchSize) {
        try {
            return super.updateBatchById(entityList, batchSize);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateAllColumnBatchById(List<T> entityList) {
        try {
            return super.updateAllColumnBatchById(entityList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public boolean updateAllColumnBatchById(List<T> entityList, int batchSize) {
        try {
            return super.updateAllColumnBatchById(entityList, batchSize);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public T selectById(Serializable id) {
        try {
            return super.selectById(id);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * @deprecated(主键ID不作为表之间关键字段，只是为了符合数据库规范)
     */
    @Deprecated
    @Override
    public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        try {
            return super.selectBatchIds(idList);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public List<T> selectByMap(Map<String, Object> columnMap) {
        try {
            columnMap.put(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectByMap(columnMap);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 重写selectOne， 覆盖LAST， 拼接 limit 1
     *
     * @param wrapper
     * @return
     */
    @Override
    public T selectOne(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            List<T> list = baseMapper.selectList(wrapper);
            if (CollectionUtils.isNotEmpty(list)) {
                int size = list.size();
                if (size > 1) {
                    throw new RuntimeException("查询出多条结果");
                }
                return list.get(0);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public Map<String, Object> selectMap(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectMap(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public Object selectObj(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectObj(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public int selectCount(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectCount(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 查询列表默认查询200条数据
     *
     * @param wrapper
     * @return
     */
    @Override
    public List<T> selectList(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectList(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 分页查询，默认分页大小最大200
     *
     * @param page
     * @return
     */
    @Override
    public Page<T> selectPage(Page<T> page) {
        if (page.getSize() > DEFAULT_PAGE_SIZE) {
            page.setSize(DEFAULT_PAGE_SIZE);
        }
        try {
            Map<String, Object> condition = new HashMap<>();
            condition.put(BasePropertiesEnum.IS_DELETE.getColumn(), 0);
            page.setCondition(condition);
            return super.selectPage(page);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public List<Map<String, Object>> selectMaps(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectMaps(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public List<Object> selectObjs(Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectObjs(wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    @Override
    public Page<Map<String, Object>> selectMapsPage(Page page, Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        if (page.getSize() > DEFAULT_PAGE_SIZE) {
            page.setSize(DEFAULT_PAGE_SIZE);
        }
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectMapsPage(page, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 分页查询，默认分页大小最大200
     *
     * @param page
     * @param wrapper
     * @return
     */
    @Override
    public Page<T> selectPage(Page<T> page, Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        if (page.getSize() > DEFAULT_PAGE_SIZE) {
            page.setSize(DEFAULT_PAGE_SIZE);
        }
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectPage(page, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 分页查询，分页大小不设限
     *
     * @param page
     * @param wrapper
     * @return
     */
    public Page<T> selectPageUnlimited(Page<T> page, Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        try {
            wrapper.eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
            return super.selectPage(page, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 按照给定的查询大小查询列表，不给size值默认查询200条数据
     *
     * @param size    查询列表的大小
     * @param wrapper 查询条件
     * @return
     */
    @Override
    public List<T> selectList(Integer size, Wrapper<T> wrapper) {
        Assert.notNull(wrapper, WRAPPER_MSG);
        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }
        Page<T> page = new Page<>(1, size);
        try {
            return selectPage(page, wrapper).getRecords();
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 传入一个entitylist,将每一个entity作为一个查询条件然后用or连接
     *
     * @param size
     * @param entities
     * @return
     */
    @Override
    public Page<T> selectPageWithAll(Integer size, List<T> entities, Integer current) {
        Wrapper<T> wrapper = new EntityWrapper<>();
        for (T entity : entities) {
            entity.setIsDelete(0);
            wrapper.orNew()
                    .allEq(JacksonTool.parseToObject(JacksonTool.parseToJsonString(entity), Map.class,
                            new ObjectMapper()))
                    .eq(BasePropertiesEnum.IS_DELETE.getColumn(), IsDeleteEnum.NO.getDeleteStatus());
        }
        Page<T> page = new Page<>(current, size);
        try {
            return selectPage(page, wrapper);
        } catch (Exception e) {
            throw new RuntimeException("数据库异常");
        }
    }

    /**
     * 条件查找列表
     * 该方法用途是：通过xml文件的SQL执行分页查找
     *
     * @param page 分页信息
     * @param t    查询条件
     * @return
     */
    @Override
    public Page<T> selectListByCondition(Page<T> page, T t) {
        if (page.getSize() > DEFAULT_PAGE_SIZE) {
            page.setSize(DEFAULT_PAGE_SIZE);
        }
        page.setRecords(baseMapper.selectListByCondition(page, t));
        return page;
    }


}
