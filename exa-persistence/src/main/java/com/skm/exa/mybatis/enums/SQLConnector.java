package com.skm.exa.mybatis.enums;

/**
 * @author dhc
 * 2019-03-06 11:34
 */                      //sql 连接器
public enum SQLConnector {
    AND, OR;

    public String getName() {
        return this.name();
    }
}
