package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分页查询时添加的条件模型")
public class QueryVo {

    @ApiModelProperty(value = "条件--编码或名称")
    private String key;
}
