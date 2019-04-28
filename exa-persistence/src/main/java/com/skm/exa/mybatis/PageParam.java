package com.skm.exa.mybatis;

import java.io.Serializable;

/**
 * 分页查询参数对象
 *
 * @author dhc
 * 2019-03-06 16:51
 */
public class PageParam<T> implements Serializable {
    private int page = 1;
    private int size = 10;
    private T condition;
    /**
     * 分页数据的数量，如果未指定，则自动查询
     */
    private Long count;

    public PageParam() {
    }

    public PageParam(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageParam(int page, int size, T condition) {
        this.page = page;
        this.size = size;
        this.condition = condition;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page < 1 ? 1 : page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size < 1 ? 1 : size;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getStart() {
        return (this.page - 1) * this.size;
    }

    public int getEnd() {
        return this.page * this.size;
    }
}
