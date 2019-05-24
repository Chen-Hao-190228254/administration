package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("添加文件关联模板")
@Data
public class FileCorrelationSaveVo{

    /**
     * 图片ID
     */
    @ApiModelProperty("图片ID集合")
    private List<Long> fileId;

    /**
     * 适用于的地方
     */
    @ApiModelProperty("适用于的地方")
    private String applyTo;
}
