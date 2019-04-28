package com.skm.exa.mybatis.enums;

/**
 * 数据库操作符
 *
 * @author dhc
 * 2019-03-06 11:15
 */
public enum SQLOperator {
    GREATER("大于", ">"),
    GREATER_EQUAL("大于等于(>=)", ">="),
    EQUAL("等于(==)", "="),
    NOTEQUAL("不等于(<>)", "<>"),
    LESS("小于(<)", "<"),
    LESS_EQUAL("小于等于(<=)", "<="),
    LIKE("包含", "LIKE"),
    LEFT_LIKE("开头包含", "LIKE"),
    IN("在列表中", "IN"),
    NOT_IN("不在列表中", "NOT IN"),
    BETWEEN("介于", "BETWEEN"),
    IS_NULL("为空", "IS NULL"),
    IS_NOT_NULL("不为空", "IS NOT NULL"),
    ;

    public final String label;
    public final String code;

    SQLOperator(String label, String code) {
        this.label = label;
        this.code = code;
    }

    public String getName() {
        return this.name();
    }

    public boolean isIn(SQLOperator operator) {
        return operator == IN;
    }

    public boolean isNotIn(SQLOperator operator) {
        return operator == NOT_IN;
    }

    public boolean isBetween(SQLOperator operator) {
        return operator == BETWEEN;
    }

    public boolean isNull(SQLOperator operator) {
        return operator == IS_NULL;
    }

    public boolean isNotNull(SQLOperator operator) {
        return operator == IS_NOT_NULL;
    }

    public boolean isLike(SQLOperator operator) {
        return operator == LIKE;
    }

    public boolean isLeftLike(SQLOperator operator) {
        return operator == LEFT_LIKE;
    }
}
