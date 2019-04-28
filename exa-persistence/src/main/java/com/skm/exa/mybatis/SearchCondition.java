package com.skm.exa.mybatis;

import com.skm.exa.mybatis.enums.SQLOperator;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 查询条件对象
 *
 * @author dhc
 * 2019-03-06 11:26
 */
public class SearchCondition implements Serializable {
    private static Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    private String property;
    private SQLOperator operator;
    private Object value;
    // 优先使用 column，为空时使用 property
    private String column;

    public SearchCondition(String column, SQLOperator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public SQLOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getColumn() {
        if (this.column != null) return column;
        Matcher matcher = HUMP_PATTERN.matcher(property);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
