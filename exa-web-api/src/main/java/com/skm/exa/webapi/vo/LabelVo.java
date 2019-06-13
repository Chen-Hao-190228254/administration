package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("标签返回模型")
public class LabelVo {

    @ApiModelProperty("标签ID")
    private Long id;

    @ApiModelProperty("标签名称")
    private String name;
}
