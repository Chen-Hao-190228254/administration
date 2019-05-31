package com.skm.exa.persistence.dto;


import lombok.Data;

import java.util.List;

@Data
public class FileSaveDto {

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

}
