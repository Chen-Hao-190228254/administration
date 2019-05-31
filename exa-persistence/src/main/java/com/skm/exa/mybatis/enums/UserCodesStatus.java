package com.skm.exa.mybatis.enums;

public enum UserCodesStatus {
    NORMAL(1),/*正常*/
    FORBIDDEN(0);/*禁用*/
    private int value;

    UserCodesStatus(int value) {
        this.value = value;
    }
    public int getValue(){
        return value ;
    }
}
