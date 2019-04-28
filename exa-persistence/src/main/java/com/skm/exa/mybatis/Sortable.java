package com.skm.exa.mybatis;

/**
 * @author dhc
 * 2019-03-07 14:35
 */
public interface Sortable {
    Sort getSort();

    void setSort(Sort sort);

    default boolean hasSort() {
        return this.getSort() != null && this.getSort().isNotEmpty();
    }

    default Sort addOrder(String property, String direction) {
        Sort sort = this.getSort();
        if (sort == null) {
            sort = new Sort();
            this.setSort(sort);
        }
        sort.put(property, direction);
        return sort;
    }

    default String getSortString() {
        if (this.getSort() == null) return "";
        return this.getSort().toOrderBy();
    }
}
