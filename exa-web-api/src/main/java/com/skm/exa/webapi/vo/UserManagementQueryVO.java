package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data/*模糊查询VO*/
@ApiModel(value = "角色管理模糊查询")
public class UserManagementQueryVO {
    @ApiModelProperty(value = "模糊搜索值")
    private String keyword;
}
