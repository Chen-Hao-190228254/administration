package com.skm.exa.persistence.dto;


import lombok.Data;

import java.util.List;

@Data
public class FileCorrelationSaveDto {

    /**
     * 关联的表名
     */
    private String correlationTableName;

    /**
     * 关联的ID
     */
    private Long correlationId;

    /**
     * 适用于的地方
     */
    private String applyTo;

    /**
     * 文件ID
     */
    private List<Long> fileId;

}
