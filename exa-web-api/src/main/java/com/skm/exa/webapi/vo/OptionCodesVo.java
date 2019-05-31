package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "选择题选项")
public class OptionCodesVo {
    @ApiModelProperty(value = "选择题选项框")
    private String bankOptionCodes;  // 选项
    @ApiModelProperty(value = "选择题内容")
    private String content ;  //内容
}
