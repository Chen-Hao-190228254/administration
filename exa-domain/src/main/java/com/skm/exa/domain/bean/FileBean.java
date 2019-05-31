package com.skm.exa.domain.bean;

import lombok.Data;

@Data
public class FileBean {

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件大小
     */
    private Double size;

    /**
     * 文件MD5数字唯一签名
     */
    private String md5;

    /**
     * 关联表名称
     */
    private String correlationTableName;

    /**
     * 关联的ID
     */
    private Long correlationId;

    /**
     * 适用于的地方，如身份证正面，身份证反面，LOGO
     */
    private String applyTo;

    public FileBean() {
    }

    public FileBean(String name, String url, Double size, String md5) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.md5 = md5;
    }
}
