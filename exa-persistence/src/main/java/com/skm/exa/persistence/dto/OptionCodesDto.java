package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.OptionCodesBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "选择题选项")
public class OptionCodesDto extends OptionCodesBean {
    private Long code ;
    @ApiModelProperty(value = "选择题选项框")
    private String bankOptionCodes;  // 选项
    @ApiModelProperty(value = "选择题内容")
    private String content ;  //内容
}
