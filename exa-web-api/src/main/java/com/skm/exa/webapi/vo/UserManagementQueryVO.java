package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data/*模糊查询VO*/
public class UserManagementQueryVO {
    @ApiModelProperty(value = "模糊搜索值")
    private String keyword;
}
