package com.skm.exa.mybatis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dhc
 * 2019-03-07 14:21   //动态查询
 */
public interface DynamicSearchable {
    List<SearchCondition> getSearchConditions();

    void setSearchConditions(List<SearchCondition> searchConditions);

    default List<SearchCondition> addSearchCondition(SearchCondition condition) {
        List<SearchCondition> conditions = this.getSearchConditions();
        if (conditions == null) {
            conditions = new ArrayList<>();
            this.setSearchConditions(conditions);
        }
        conditions.add(condition);
        return conditions;
    }

    List<SearchConditionGroup> getSearchConditionGroups();

    void setSearchConditionGroups(List<SearchConditionGroup> searchConditionGroups);
    /*  addSearchConditionGroup 添加搜索条件组 */
    default List<SearchConditionGroup> addSearchConditionGroup(SearchConditionGroup group) {
        List<SearchConditionGroup> groups = this.getSearchConditionGroups();
        if (groups == null) {
            groups = new ArrayList<>();
            this.setSearchConditionGroups(groups);
        }
        groups.add(group);
        return groups;
    }
}
