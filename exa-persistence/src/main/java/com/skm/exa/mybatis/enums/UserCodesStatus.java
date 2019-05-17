package com.skm.exa.mybatis.enums;

public enum UserCodesStatus {
    NORMAL(0),/*正常*/
    FORBIDDEN(1);/*禁用*/
    private int value;

    UserCodesStatus(int value) {
        this.value = value;
    }
    public int getValue(){
        return value ;
    }
}
