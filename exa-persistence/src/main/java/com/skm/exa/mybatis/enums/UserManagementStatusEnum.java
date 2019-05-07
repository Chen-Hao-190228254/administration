package com.skm.exa.mybatis.enums;

import java.util.stream.Stream;

public enum UserManagementStatusEnum {
    NORMAL(0),   /*正常*/
    FORBIDDEN(1),    /*禁用*/
    VOID (2);       /*无效*/
    private int value;

    UserManagementStatusEnum(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public static UserManagementStatusEnum valueOf(int value) {
        return Stream.of(values()).filter((e) -> e.getValue() == value).findFirst().orElse(null);
    }
}
