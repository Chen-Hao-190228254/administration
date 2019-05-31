package com.skm.exa.domain.bean;


import lombok.Data;

@Data
public class StatusBean {

    /**
     * 状态编码
     */
    private Byte code;


    /**
     * 状态名称
     */
    private String name;


    public StatusBean() {
    }

    public StatusBean(Byte code, String name) {
        this.code = code;
        this.name = name;
    }
}
