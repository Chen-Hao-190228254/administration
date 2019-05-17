package com.skm.exa.common.enums;

public enum  StatusEnum {

    NORMAL("正常",1),
    FORBIDDEN("禁用",0),
    LOGOUT("注销",-1);

    private String name;
    private byte index;

    StatusEnum(String name, int index) {
        this.name = name;
        this.index = (byte)index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "StatusEnum{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
