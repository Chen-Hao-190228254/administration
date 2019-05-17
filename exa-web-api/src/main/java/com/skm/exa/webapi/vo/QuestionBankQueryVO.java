package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class QuestionBankQueryVO {
    @ApiModelProperty(value = "模糊搜索值")
    private String keyword ;
}
