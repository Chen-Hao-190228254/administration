package com.skm.exa.domain.bean;

import lombok.Data;

@Data
public class AreaBean {


    /**
     * 地址ID
     */
    private String id;

    /**
     * 地址编码
     */
    private Long code;

    /**
     * 地址名称
     */
    private String name;

    /**
     * 地址等级
     */
    private byte level;

    /**
     * 地址上一级的ID
     */
    private Long parentCode;


}
