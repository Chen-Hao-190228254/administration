package com.skm.exa.domain.bean;

import lombok.Data;

@Data
public class ImageBean {

    /**
     * 图片ID
     */
    private Long id;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片大小
     */
    private Long size;

    /**
     * 文件MD5数字唯一签名
     */
    private String md5;

}
