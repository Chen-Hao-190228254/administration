package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data //代码模糊查询
public class UserCodesQueryVO  {
    @ApiModelProperty(value = "搜索值")
    private String keyword;  //
}
