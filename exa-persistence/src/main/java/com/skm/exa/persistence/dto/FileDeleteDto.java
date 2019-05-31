package com.skm.exa.persistence.dto;

import lombok.Data;

import java.util.List;


@Data
public class FileDeleteDto {

    /**
     * 文件ID集合
     */
    private List<Long> fileIds;

    /**
     * 文件名称集合
     */
    private List<String> filenames;

    public FileDeleteDto() {
    }

    public FileDeleteDto(List<Long> fileIds, List<String> filenames) {
        this.fileIds = fileIds;
        this.filenames = filenames;
    }
}
