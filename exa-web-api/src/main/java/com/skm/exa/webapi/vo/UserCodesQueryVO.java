package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data //代码模糊查询
@ApiModel(value = "代码管理查询VO")
public class UserCodesQueryVO  {
    @ApiModelProperty(value = "搜索值")
    private String keyword;  //
}
