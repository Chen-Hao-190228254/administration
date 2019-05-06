package com.skm.exa.mybatis;

import com.skm.exa.mybatis.enums.SQLConnector;
import com.skm.exa.mybatis.enums.SQLOperator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SQL 查询条件组
 *
 * @author dhc
 * 2019-03-06 11:30     // SearchConditionGroup 搜索条件组
 */
public class SearchConditionGroup implements Serializable {
    private SQLConnector outerConnector;    //外部连接器
    private SQLConnector innerConnector;    //内部连接器
    private List<SearchCondition> conditions;   // 条件

    public SearchConditionGroup(SQLConnector outerConnector, SQLConnector innerConnector, List<SearchCondition> conditions) {
        this.outerConnector = outerConnector;
        this.innerConnector = innerConnector;
        this.conditions = conditions;
    }
    /* buildMultiColumnsSearch  构建多列搜索*/
    public static SearchConditionGroup buildMultiColumnsSearch(String searchString, String... columns) {
        return buildMultiColumnsSearch(SQLConnector.AND, searchString, columns);
    }

    public static SearchConditionGroup buildMultiColumnsSearch(SQLConnector outerConnector,
                                                               String searchString, String... columns) {
        if (columns.length == 0) return null;
        List<SearchCondition> conditions = Arrays.stream(columns).map(c -> new SearchCondition(c, SQLOperator.LIKE, searchString)).collect(Collectors.toList());
        return new SearchConditionGroup(outerConnector, SQLConnector.OR, conditions);
    }

    public SQLConnector getOuterConnector() {
        return outerConnector;
    }

    public void setOuterConnector(SQLConnector outerConnector) {
        this.outerConnector = outerConnector;
    }

    public SQLConnector getInnerConnector() {
        return innerConnector;
    }

    public void setInnerConnector(SQLConnector innerConnector) {
        this.innerConnector = innerConnector;
    }

    public List<SearchCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<SearchCondition> conditions) {
        this.conditions = conditions;
    }
}
