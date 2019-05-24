package com.skm.exa.persistence.dto;

import lombok.Data;

@Data
public class ImageCorrelationSaveDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 关联ID
     */
    private Long correlationId;

    /**
     * 图片ID
     */
    private Long fileId;

    /**
     * 关联表的名称
     */
    private String correlationTableName;

    /**
     * 适用于哪里
     */
    private String applyTo;
}
