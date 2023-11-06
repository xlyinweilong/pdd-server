package com.yin.pddserver.common.base.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.yin.pddserver.common.base.po.BasePo;
import com.yin.pddserver.common.base.service.BaseWisdomService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BaseWisdomServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseWisdomService<T> {

    @Override
    public boolean saveOrUpdate(T entity) {
        if (entity instanceof BasePo) {
            BasePo b = (BasePo) entity;
            b.setUpdateDate(new Date());
        }
        boolean b = super.saveOrUpdate(entity);
        if (!b) {
            throw new MybatisPlusException("乐观锁异常，请重新尝试操作");
        }
        return b;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList) {
        return this.saveOrUpdateBatch(entityList, 1000);
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        if (entityList.isEmpty()) {
            return true;
        }
        entityList.stream().filter(t -> t instanceof BasePo).forEach(t -> {
            BasePo b = (BasePo) t;
            b.setUpdateDate(new Date());
        });
        boolean b = super.saveOrUpdateBatch(entityList, 1000);
        if (!b) {
            throw new MybatisPlusException("乐观锁异常，请重新尝试操作");
        }
        return b;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean saveBatch(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(currentModelClass())) {
            int size = entityList.size();
            String sqlStatement = getSqlStatement(SqlMethod.INSERT_ONE);
            for (int i = 0; i < size; i++) {
                batchSqlSession.insert(sqlStatement, entityList.get(i));
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
        }
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public boolean saveBatch(List<T> entityList) {
        return this.saveBatch(entityList, 1000);
    }
}
