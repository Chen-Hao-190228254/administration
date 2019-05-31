package com.skm.exa.persistence.dto;

import lombok.Data;

import java.util.List;


@Data
public class FileSelectDto {


    /**
     * 关联的表名
     */
    private String correlationTableName;


    /**
     * 关联的ID
     */
    private List<Long> correlationIds;

    /**
     * 适用于的地方
     */
    private List<String> applyTos;


    /**
     * 文件ID
     */
    private List<Long> fileIds;

    /**
     * 文件名称
     */
    private List<String> filenames;

    public FileSelectDto() {
    }

    public FileSelectDto(String correlationTableName, List<Long> correlationIds) {
        this.correlationTableName = correlationTableName;
        this.correlationIds = correlationIds;
    }
}
