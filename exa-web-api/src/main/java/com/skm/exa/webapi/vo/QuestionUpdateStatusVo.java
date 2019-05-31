package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("更改状态")
public class QuestionUpdateStatusVo {
    private Long id;
    @ApiModelProperty(value = "0，禁用 1，正常")
    private Long status;
}
