package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserBean;
import com.skm.exa.mybatis.*;

import java.util.List;

/**
 * @author dhc
 * 2019-03-07 14:01
 */
public class UserQO extends UserBean implements DynamicSearchable, Sortable {
    private String usernameLike;
    private String realnameLike;

    private List<Long> ids;
    private String searchLike;

    private List<SearchCondition> searchConditions;
    private List<SearchConditionGroup> searchConditionGroups;
    private Sort sort;

    public String getUsernameLike() {
        return usernameLike;
    }

    public void setUsernameLike(String usernameLike) {
        this.usernameLike = usernameLike;
    }

    public String getRealnameLike() {
        return realnameLike;
    }

    public void setRealnameLike(String realnameLike) {
        this.realnameLike = realnameLike;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getSearchLike() {
        return searchLike;
    }

    public void setSearchLike(String searchLike) {
        this.searchLike = searchLike;
    }

    @Override
    public List<SearchCondition> getSearchConditions() {
        return searchConditions;
    }

    @Override
    public void setSearchConditions(List<SearchCondition> searchConditions) {
        this.searchConditions = searchConditions;
    }

    @Override
    public List<SearchConditionGroup> getSearchConditionGroups() {
        return searchConditionGroups;
    }

    @Override
    public void setSearchConditionGroups(List<SearchConditionGroup> searchConditionGroups) {
        this.searchConditionGroups = searchConditionGroups;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
