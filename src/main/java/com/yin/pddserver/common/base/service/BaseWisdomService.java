package com.yin.pddserver.common.base.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

public interface BaseWisdomService<T> extends IService<T> {

    boolean saveOrUpdate(T entity);

    boolean saveOrUpdateBatch(Collection<T> entityList);

    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    boolean saveBatch(List<T> entityList, int batchSize);

    boolean saveBatch(List<T> entityList);
}
