package com.skm.exa.service;

import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.BaseBean;
import com.skm.exa.mybatis.BatchSaveParameter;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author dhc
 * 2019-03-07 15:42
 */
public abstract class BaseServiceImpl<T extends BaseBean, D extends BaseDao<T>> implements BaseService<T> {
    @Autowired
    protected D dao;

    @Override
    public T get(long id) {
        return dao.get(id);
    }

    @Override
    public T find(Object condition) {
        List<T> list = this.getList(condition);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean has(Object condition) {
        List<T> list = this.getList(condition);
        return list != null && list.size() > 0;
    }

    @Override
    public List<T> getList(Object condition) {
        return dao.select(condition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> List<V> getList(Object condition, Class<V> vClass) {
        List<T> list = this.getList(condition);
        if (list == null || list.isEmpty()) return new ArrayList<>();
        Class<T> beanClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return BeanMapper.mapList(list, beanClass, vClass);
    }

    @Override
    public Page<T> getPage(PageParam<?> pageParam) {
        return dao.selectPage(pageParam);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends Serializable> Page<V> getPage(PageParam<?> pageParam, Class<V> vClass) {
        Page<T> page = this.getPage(pageParam);
        Class<T> beanClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return page.map(beanClass, vClass);
    }

    private void preInsert(BaseBean bean) {
        bean.setEntryDt(new Date());
        this.preUpdate(bean);
    }

    private void preUpdate(BaseBean bean) {
        bean.setUpdateDt(new Date());
    }

    @Override
    public void save(T bean) {
        if (bean.getId() == null) {
            this.preInsert(bean);
            dao.insert(bean);
        } else {
            this.preUpdate(bean);
            dao.update(bean);
        }
    }

    @Override
    @Transactional
    public void saveAll(Collection<T> beans) {
        if (beans == null || beans.isEmpty()) return;

        Set<T> toInserts = new HashSet<>();
        for (T bean : beans) {
            if (bean.getId() == null) {
                preInsert(bean);
                toInserts.add(bean);
            } else {
                preUpdate(bean);
                dao.update(bean);
            }
        }
        if (!toInserts.isEmpty()) {
            dao.batchInsert(BatchSaveParameter.of(toInserts));
        }
    }

    @Override
    public void dynamicUpdate(T bean) {
        dao.dynamicUpdate(bean);
    }

    @Override
    public int delete(long id) {
        return dao.delete(id);
    }

    @Override
    public int deleteByIds(Collection<Long> ids) {
        return dao.deleteByIds(ids);
    }
}
