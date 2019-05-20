package com.skm.exa.persistence.dto;

import lombok.Data;

@Data
public class ImageCorrelationSaveDto {


    /**
     * 关联ID
     */
    private Long correlationId;

    /**
     * 图片ID
     */
    private Long imageId;

    /**
     * 关联表面
     */
    private String correlationTableName;
}
