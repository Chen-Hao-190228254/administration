package com.skm.exa.mybatis.enums;

public enum  UserCodesEditStatusEnum {
    EDIT(0),    /*可编辑*/
    NO_EDIT(1);     /*不可编辑*/
    private int value;

    UserCodesEditStatusEnum(int value){
        this.value = value ;
    }
    public int getValue(){
        return value;
    }
}
